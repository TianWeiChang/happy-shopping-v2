package com.tian.service;

import com.tian.mapper.GoodsCategoryMapper;
import com.tian.mapper.MallGoodsMapper;
import com.tian.entity.GoodsCategory;
import com.tian.entity.GoodsInfo;
import com.tian.enums.MallCategoryLevelEnum;
import com.tian.enums.ServiceResultEnum;
import com.tian.exception.MallException;
import com.tian.utils.BeanUtil;
import com.tian.utils.PageResult;
import com.tian.vo.GoodsListVO;
import com.tian.vo.SearchGoodsListVO;
import com.tian.vo.SearchGoodsVO;
import com.tian.vo.StockNumDTO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DubboService(version = "1.0.0")
public class MallGoodsServiceImpl implements MallGoodsService {

    @Resource
    private MallGoodsMapper goodsMapper;
    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult getMallGoodsPage(GoodsListVO goodsListVO) {
        List<GoodsInfo> goodsList = goodsMapper.findNewBeeMallGoodsList(goodsListVO);
        int total = goodsMapper.getTotalNewBeeMallGoods(goodsListVO);
        PageResult pageResult = new PageResult(goodsList, total, goodsListVO.getLimit(), goodsListVO.getPage());
        return pageResult;
    }

    @Override
    public String saveMallGoods(GoodsInfo goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveMallGoods(List<GoodsInfo> goodsInfoList) {
        if (!CollectionUtils.isEmpty(goodsInfoList)) {
            goodsMapper.batchInsert(goodsInfoList);
        }
    }

    @Override
    public String updateMallGoods(GoodsInfo goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        GoodsInfo temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        GoodsInfo temp2 = goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(goods.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public GoodsInfo getGoodsById(Long id) {
        GoodsInfo goodsInfo = goodsMapper.selectByPrimaryKey(id);
        if (goodsInfo == null) {
            MallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return goodsInfo;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public List<GoodsInfo> getGoodsListByIds(List<Long> ids) {
        return goodsMapper.selectByPrimaryKeys(ids);
    }

    @Override
    public PageResult searchNewBeeMallGoods(SearchGoodsListVO searchGoodsListVO) {
        List<GoodsInfo> goodsList = goodsMapper.findNewBeeMallGoodsListBySearch(searchGoodsListVO);
        int total = goodsMapper.getTotalNewBeeMallGoodsBySearch(searchGoodsListVO);
        List<SearchGoodsVO> searchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            searchGoodsVOS = BeanUtil.copyList(goodsList, SearchGoodsVO.class);
            for (SearchGoodsVO searchGoodsVO : searchGoodsVOS) {
                String goodsName = searchGoodsVO.getGoodsName();
                String goodsIntro = searchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    searchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    searchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(searchGoodsVOS, total, searchGoodsListVO.getLimit(), searchGoodsListVO.getPage());
        return pageResult;
    }

    @Override
    public int updateStockNum(List<StockNumDTO> stockNumDTOS) {
        return goodsMapper.updateStockNum(stockNumDTOS);
    }
}
