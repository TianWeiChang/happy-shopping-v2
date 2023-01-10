package com.tian.service;

import com.tian.configs.RedisLockV2;
import com.tian.configs.RedisUtil;
import com.tian.constants.Constants;
import com.tian.constants.RedisCacheKey;
import com.tian.dao.MallShoppingCartItemMapper;
import com.tian.dto.CartItemDTO;
import com.tian.entity.GoodsInfo;
import com.tian.entity.MallShoppingCartItem;
import com.tian.enums.ServiceResultEnum;
import com.tian.mq.MQMsgSender;
import com.tian.utils.BeanUtil;
import com.tian.vo.ShoppingCartItemVO;
import com.tian.vo.StockNumDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@DubboService(version = "1.0.0")
public class MallCartServiceImpl implements MallCartService {

    @Resource
    private MallShoppingCartItemMapper mallShoppingCartItemMapper;

    @DubboReference(version = "1.0.0")
    private MallGoodsService mallGoodsService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisLockV2 redisLockV2;

    @Resource
    private MQMsgSender mqMsgSender;

    @Override
    public String saveMallCartItem(MallShoppingCartItem mallShoppingCartItem, Long userId) {
//        String lockKey = RedisCacheKey.GOODS_STOCK_NUM_LOCK_KEY + mallShoppingCartItem.getGoodsId();
        String lockKey = RedisCacheKey.GOODS_STOCK_NUM_LOCK_KEY + userId;
        String lock = redisLockV2.tryLock(lockKey, 5000L);

        try {
            //获取库存量
            Object stockNumVal = redisUtil.get(RedisCacheKey.GOODS_STOCK_NUM_KEY + mallShoppingCartItem.getGoodsId());
            //如果不存在，证明商品在录入时候出问题
            if (stockNumVal == null) {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
            int stockNum = (int) stockNumVal;

            //校验库存量是否充足
            if (mallShoppingCartItem.getGoodsCount() > stockNum) {
                return ServiceResultEnum.SHOPPING_CART_ITEM_STOCK_NUMBER_ERROR.getResult();
            }
            //查询用户的购物车中是否已经有同一物品了
            MallShoppingCartItem temp = mallShoppingCartItemMapper.selectByUserIdAndGoodsId(mallShoppingCartItem.getUserId(), mallShoppingCartItem.getGoodsId());
            //已添加过购物车
            if (temp != null) {
                //Redis中库存量扣减
                redisUtil.set(RedisCacheKey.GOODS_STOCK_NUM_KEY + mallShoppingCartItem.getGoodsId(), stockNum - mallShoppingCartItem.getGoodsCount());

                List<StockNumDTO> stockNumDTOS = new ArrayList<>();
                StockNumDTO stockNumDTO = new StockNumDTO();
                stockNumDTO.setGoodsCount(mallShoppingCartItem.getGoodsCount());
                stockNumDTO.setGoodsId(mallShoppingCartItem.getGoodsId());
                stockNumDTOS.add(stockNumDTO);
                //数据库中物品数量扣减
                mallGoodsService.updateStockNum(stockNumDTOS);

                //购物车加入物品数量增加
                temp.setGoodsCount(temp.getGoodsCount() + mallShoppingCartItem.getGoodsCount());
                CartItemDTO cartItemDTO = updateNewBeeMallCartItem(temp, userId);
                if (cartItemDTO.getRetMsg().equals(ServiceResultEnum.SUCCESS.getResult())) {
                    return ServiceResultEnum.SUCCESS.getResult();
                }
                return cartItemDTO.getRetMsg();
            }
            GoodsInfo goodsInfo = mallGoodsService.getGoodsById(mallShoppingCartItem.getGoodsId());
            //商品为空
            if (goodsInfo == null) {
                return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
            }
            int totalItem = mallShoppingCartItemMapper.selectCountByUserId(mallShoppingCartItem.getUserId()) + 1;
            //超出单个商品的最大数量
            if (mallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
                return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
            }
            //超出最大数量
            if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
                return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
            }
            //保存记录
            if (mallShoppingCartItemMapper.insertSelective(mallShoppingCartItem) > 0) {
                //Redis中库存量扣减
                redisUtil.set(RedisCacheKey.GOODS_STOCK_NUM_KEY + mallShoppingCartItem.getGoodsId(), stockNum - mallShoppingCartItem.getGoodsCount());
                List<StockNumDTO> stockNumDTOS = new ArrayList<>();
                StockNumDTO stockNumDTO = new StockNumDTO();
                stockNumDTO.setGoodsCount(mallShoppingCartItem.getGoodsCount());
                stockNumDTO.setGoodsId(mallShoppingCartItem.getGoodsId());
                stockNumDTOS.add(stockNumDTO);
                mallGoodsService.updateStockNum(stockNumDTOS);
                int oldCount = (int) redisUtil.get(RedisCacheKey.USER_CART_COUNT_KEY + mallShoppingCartItem.getUserId());
                redisUtil.set(RedisCacheKey.USER_CART_COUNT_KEY + mallShoppingCartItem.getUserId(), oldCount + mallShoppingCartItem.getGoodsCount());
                return ServiceResultEnum.SUCCESS.getResult();
            }
        } finally {
            //释放锁，其他线程可以购买了
            redisLockV2.release(lockKey, lock);
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public CartItemDTO updateNewBeeMallCartItem(MallShoppingCartItem mallShoppingCartItem, Long userId) {
        //获取已加入购物车的物品
        MallShoppingCartItem mallShoppingCartItemUpdate = mallShoppingCartItemMapper.selectByPrimaryKey(mallShoppingCartItem.getCartItemId());
        CartItemDTO cartItemDTO = new CartItemDTO();
        if (mallShoppingCartItemUpdate == null) {
            cartItemDTO.setRetMsg(ServiceResultEnum.DATA_NOT_EXIST.getResult());
            return cartItemDTO;
        }
        //超出单个商品的最大数量
        if (mallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            cartItemDTO.setRetMsg(ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult());
            return cartItemDTO;
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!mallShoppingCartItemUpdate.getUserId().equals(mallShoppingCartItem.getUserId())) {
            cartItemDTO.setRetMsg(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            return cartItemDTO;
        }
        //数值相同，则不执行数据操作
        if (mallShoppingCartItem.getGoodsCount().equals(mallShoppingCartItemUpdate.getGoodsCount())) {
            cartItemDTO.setRetMsg(ServiceResultEnum.SUCCESS.getResult());
            return cartItemDTO;
        }
        int newCount = (int) redisUtil.get(RedisCacheKey.USER_CART_COUNT_KEY + mallShoppingCartItem.getUserId());

        //减少购物车物品购买数量
        int flag = 0;
        if (mallShoppingCartItemUpdate.getGoodsCount() > mallShoppingCartItem.getGoodsCount()) {
            newCount = newCount - (mallShoppingCartItemUpdate.getGoodsCount() - mallShoppingCartItem.getGoodsCount());
        } else {
            //增加购物车物品购买数量
            newCount = newCount + (mallShoppingCartItem.getGoodsCount() - mallShoppingCartItemUpdate.getGoodsCount());
        }
        mallShoppingCartItemUpdate.setGoodsCount(mallShoppingCartItem.getGoodsCount());
        mallShoppingCartItemUpdate.setUpdateTime(new Date());
        cartItemDTO.setFlag(flag);
        //修改记录
        if (mallShoppingCartItemMapper.updateByPrimaryKeySelective(mallShoppingCartItemUpdate) > 0) {
            redisUtil.set(RedisCacheKey.USER_CART_COUNT_KEY + mallShoppingCartItem.getUserId(), newCount);
            flag = 1;
            cartItemDTO.setRetMsg(ServiceResultEnum.SUCCESS.getResult());
            cartItemDTO.setFlag(flag);
            return cartItemDTO;
        }
        cartItemDTO.setRetMsg(ServiceResultEnum.DB_ERROR.getResult());
        return cartItemDTO;
    }

    @Override
    public MallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId) {
        return mallShoppingCartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        MallShoppingCartItem mallShoppingCartItem = mallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (mallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(mallShoppingCartItem.getUserId())) {
            return false;
        }
        return mallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId) {
        List<ShoppingCartItemVO> shoppingCartItemVOS = new ArrayList<>();
        List<MallShoppingCartItem> mallShoppingCartItems = mallShoppingCartItemMapper.selectByUserId(userId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(mallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> newBeeMallGoodsIds = mallShoppingCartItems.stream().map(MallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<GoodsInfo> goodInfos = mallGoodsService.getGoodsListByIds(newBeeMallGoodsIds);
            Map<Long, GoodsInfo> newBeeMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(goodInfos)) {
                newBeeMallGoodsMap = goodInfos.stream().collect(Collectors.toMap(GoodsInfo::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (MallShoppingCartItem mallShoppingCartItem : mallShoppingCartItems) {
                ShoppingCartItemVO shoppingCartItemVO = new ShoppingCartItemVO();
                BeanUtil.copyProperties(mallShoppingCartItem, shoppingCartItemVO);
                if (newBeeMallGoodsMap.containsKey(mallShoppingCartItem.getGoodsId())) {
                    GoodsInfo goodsInfoTemp = newBeeMallGoodsMap.get(mallShoppingCartItem.getGoodsId());
                    shoppingCartItemVO.setGoodsCoverImg(goodsInfoTemp.getGoodsCoverImg());
                    String goodsName = goodsInfoTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    shoppingCartItemVO.setGoodsName(goodsName);
                    shoppingCartItemVO.setSellingPrice(goodsInfoTemp.getSellingPrice());
                    shoppingCartItemVOS.add(shoppingCartItemVO);
                }
            }
        }
        return shoppingCartItemVOS;
    }

    @Override
    public void refreshCartCountRedis(Long userId) {
        mqMsgSender.sendRefreshCartCountRedis(userId);
    }
}
