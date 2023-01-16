package com.tian.controller;

import com.tian.configs.RedisUtil;
import com.tian.constants.Constants;
import com.tian.constants.RedisCacheKey;
import com.tian.dto.OrderListDTO;
import com.tian.entity.MallOrder;
import com.tian.enums.MallOrderStatusEnum;
import com.tian.enums.ServiceResultEnum;
import com.tian.exception.MallException;
import com.tian.mq.MQMsgSender;
import com.tian.service.MallCartService;
import com.tian.service.MallOrderService;
import com.tian.service.UserRemindService;
import com.tian.utils.*;
import com.tian.vo.MallUserVO;
import com.tian.vo.OrderDetailVO;
import com.tian.vo.ShoppingCartItemVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @DubboReference(version = "1.0.0", check = false)
    private MallCartService mallCartService;
    @DubboReference(version = "1.0.0", check = false)
    private MallOrderService mallOrderService;
    @DubboReference(version = "1.0.0", check = false)
    private UserRemindService userRemindService;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MQMsgSender mqMsgSender;

    // 提交订单执行完成到这个方法
    //然后到 订单详情页面  （页面中有 去付款 按钮）
    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        OrderDetailVO orderDetailVO = mallOrderService.getOrderDetailByOrderNo(orderNo, user.getUserId());
        request.setAttribute("orderDetailVO", orderDetailVO);
        return "mall/order-detail";
    }

    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {

        MallUserVO user = UserSessionUtil.userSession(httpSession);
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据

        OrderListDTO orderListDTO = new OrderListDTO(Integer.parseInt(params.get("page").toString()), Constants.ORDER_SEARCH_PAGE_LIMIT);
        orderListDTO.setUserId(user.getUserId());

        request.setAttribute("orderPageResult", mallOrderService.getMyOrders(orderListDTO));
        request.setAttribute("path", "orders");
        return "mall/my-orders";
    }

    /**
     * 提交订单
     */
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {

        MallUserVO user = UserSessionUtil.userSession(httpSession);
        //通过session中的userId 获取该用户购物车信息
        List<ShoppingCartItemVO> myShoppingCartItems = mallCartService.getMyShoppingCartItems(user.getUserId());
        //是否填写好地址
        if (StringUtils.isEmpty(user.getAddress().trim())) {
            //无收货地址
            MallException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        //购物车没有，需要先加入购物车
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            MallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        /**
         * 如果需要做防重复下单，这里就可以做订单重复提交校验1         */
        String saveOrderResult = mallOrderService.saveOrder(user, myShoppingCartItems);
        //session中设置购物车数量
        int count = (int) redisUtil.get(RedisCacheKey.USER_CART_COUNT_KEY + user.getUserId());
        httpSession.setAttribute("cartCount", count);
        //跳转到订单详情页
        return "redirect:/orders/" + saveOrderResult;
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        String cancelOrderResult = mallOrderService.cancelOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        String finishOrderResult = mallOrderService.finishOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    //点击去支付 来到这个方法
    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {

        MallUserVO user = UserSessionUtil.userSession(httpSession);

        MallOrder mallOrder = mallOrderService.getMallOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getUserId().equals(mallOrder.getUserId())) {
            MallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (mallOrder.getOrderStatus().intValue() != MallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MallException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", mallOrder.getTotalPrice());
        return "mall/pay-select";
    }

    //付款页面  选择 支付方式 返回想要支付二维码
    //目前就搞了两个支付码，微信和支付宝
    //实际的开发中，这里是调用不同支付渠道的接口，然后跳转到对应的支付页面
    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        MallOrder mallOrder = mallOrderService.getMallOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getUserId().equals(mallOrder.getUserId())) {
            MallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (mallOrder.getOrderStatus().intValue() != MallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MallException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", mallOrder.getTotalPrice());
        // TODO: 2022/3/27 修改订单支付渠道
        //可以理解为  这个页面是支付渠道提供的
        if (payType == 1) {
            return "mall/alipay";
        } else {
            return "mall/wxpay";
        }
    }

    //页面上 点击 支付完成  来到这个方法
    //实际开发过程中是 在去支付的时候，带上我们的接口，然后支付渠道支付完成立马跳转到我们这个接口
    //这是正常情况下，我们就处理为支付成功了，但是如果用户不点返回，或者由于网络问题，导致我们没有拿
    //到渠道返回的信息，此时，我们会提同一个回调接口payNotify()，给渠道回调通知，但是回调通知也有可能收不到，
    // 此时，我们可以启动一个定时任务，去渠道查询订单状态，从而
    // 根据渠道的状态来更新我们数据库中的订单状态
    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = mallOrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

    @PostMapping
    public void payNotify() {
        //解密
        //校验参数
        //校验订单状态
    }

    @PutMapping("/orders/{orderNo}/remind")
    @ResponseBody
    public Result remindOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        String cancelOrderResult = userRemindService.remind(orderNo, user.getUserId(), user.getLoginName());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

}
