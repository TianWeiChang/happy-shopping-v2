package com.tian.service;


import com.tian.dto.CartItemDTO;
import com.tian.entity.MallShoppingCartItem;
import com.tian.vo.ShoppingCartItemVO;

import java.util.List;

public interface MallCartService {

    /**
     * 保存商品至购物车中
     */
    String saveNewBeeMallCartItem(MallShoppingCartItem mallShoppingCartItem, Long userId);

    /**
     * 修改购物车中的属性
     */
    CartItemDTO updateNewBeeMallCartItem(MallShoppingCartItem mallShoppingCartItem, Long userId);

    /**
     * 获取购物项详情
     */
    MallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);

    /**
     * 获取我的购物车中的列表数据
     *
     */
    List<ShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId);

    void refreshCartCountRedis(Long userId);
}
