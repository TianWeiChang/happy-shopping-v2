package com.tian.service;


import com.tian.dto.OrderListDTO;
import com.tian.entity.MallOrder;
import com.tian.utils.PageResult;
import com.tian.vo.MallUserVO;
import com.tian.vo.OrderDetailVO;
import com.tian.vo.OrderItemVO;
import com.tian.vo.ShoppingCartItemVO;

import java.util.List;

public interface MallOrderService {
    /**
     * 后台分页
     */
    PageResult getMallOrdersPage(OrderListDTO orderListDTO);

    /**
     * 订单信息修改
     *
     * @param mallOrder
     * @return
     */
    String updateOrderInfo(MallOrder mallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    /**
     * 保存订单
     */
    String saveOrder(MallUserVO user, List<ShoppingCartItemVO> myShoppingCartItems);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    MallOrder getMallOrderByOrderNo(String orderNo);

    /**
     * 我的订单列表
     */
    PageResult getMyOrders(OrderListDTO orderListDTO);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    List<OrderItemVO> getOrderItems(Long id);
}
