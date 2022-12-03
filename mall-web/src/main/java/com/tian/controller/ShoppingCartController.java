package com.tian.controller;

import com.tian.configs.RedisUtil;
import com.tian.constants.RedisCacheKey;
import com.tian.dto.CartItemDTO;
import com.tian.entity.MallShoppingCartItem;
import com.tian.enums.ServiceResultEnum;
import com.tian.exception.MallException;
import com.tian.service.MallCartService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.utils.UserSessionUtil;
import com.tian.vo.MallUserVO;
import com.tian.vo.ShoppingCartItemVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShoppingCartController {

    @DubboReference(version = "1.0.0", check = false)
    private MallCartService mallCartService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 通过session中用户的userId查询该用户的购物车物品（数量+价格）
     * 统计购物车物品总数
     * 统计购物车物品总价格
     */
    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        if (user == null) {
            MallException.fail("请重新登陆");
        }

        int itemsTotal = 0;
        int priceTotal = 0;

        List<ShoppingCartItemVO> myShoppingCartItems = mallCartService.getMyShoppingCartItems(user.getUserId());

        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数计算
            itemsTotal = myShoppingCartItems.stream().mapToInt(ShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                MallException.fail("购物项不能为空");
            }
            //购物车物品总价计算
            for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
                priceTotal += shoppingCartItemVO.getGoodsCount() * shoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MallException.fail("购物项价格异常");
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/cart";
    }

    /**
     * 加入购物车
     * 如果购物车有同样的商品，则只需要修改购物车商品数量，同时修改商品库存量
     * 如果购物车中没有同样的商品，则购物车表新增一条商品信息，同时修改商品库存量
     */
    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveNewBeeMallShoppingCartItem(@RequestBody MallShoppingCartItem mallShoppingCartItem,
                                                 HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        mallShoppingCartItem.setUserId(user.getUserId());

        String saveResult = mallCartService.saveNewBeeMallCartItem(mallShoppingCartItem, user.getUserId());
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            int oldCount = (int) redisUtil.get(RedisCacheKey.USER_CART_COUNT_KEY + mallShoppingCartItem.getUserId());
            httpSession.setAttribute("cartCount", oldCount);
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    /**
     * 购物车物品数量 添加减少
     * 都是会修改购物车中商品的数量，只是一个是增加，一个是减少
     * 当前端传过来的购买量少于购物车中商品梳理，则是减少，否则是继续添加
     */
    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@RequestBody MallShoppingCartItem mallShoppingCartItem,
                                                   HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        mallShoppingCartItem.setUserId(user.getUserId());
        CartItemDTO cartItemDTO = mallCartService.updateNewBeeMallCartItem(mallShoppingCartItem, user.getUserId());
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(cartItemDTO.getRetMsg())) {
            if (cartItemDTO.getFlag() == 1) {
                int count = (int) redisUtil.get(RedisCacheKey.USER_CART_COUNT_KEY + mallShoppingCartItem.getUserId());
                httpSession.setAttribute("cartCount", count);
            }
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(cartItemDTO.getRetMsg());
    }

    /**
     * 删除购物车中某个商品的记录（同样的商品全部删除）
     */
    @DeleteMapping("/shop-cart/{mallShoppingCartItemId}")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("mallShoppingCartItemId") Long mallShoppingCartItemId,
                                                   HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        Boolean deleteResult = mallCartService.deleteById(mallShoppingCartItemId, user.getUserId());
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    //获取结算页面的信息，如果没有加入购物车，则不需要跳转到结算页面
    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        List<ShoppingCartItemVO> myShoppingCartItems = mallCartService.getMyShoppingCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
                priceTotal += shoppingCartItemVO.getGoodsCount() * shoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MallException.fail("购物项价格异常");
            }
        }
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/order-settle";
    }
}
