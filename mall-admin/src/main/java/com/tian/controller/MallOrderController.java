package com.tian.controller;

import com.tian.constants.Constants;
import com.tian.dto.OrderListDTO;
import com.tian.entity.MallOrder;
import com.tian.entity.OrderRemind;
import com.tian.enums.ServiceResultEnum;
import com.tian.service.MallOrderService;
import com.tian.service.UserRemindService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.vo.OrderItemVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tianwc
 * @date 2022-11-20
 * <p>
 * 订单管理
 */
@Controller
@RequestMapping
public class MallOrderController {

    @DubboReference(version = "1.0.0", check = false)
    private MallOrderService mallOrderService;
    @DubboReference(version = "1.0.0", check = false)
    private UserRemindService userRemindService;

    @GetMapping("/orders")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "orders");
        return "admin/mall_order";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/orders/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        OrderListDTO orderListDTO = new OrderListDTO(Integer.parseInt(params.get("page").toString()), Constants.ORDER_SEARCH_PAGE_LIMIT);
        return ResultGenerator.genSuccessResult(mallOrderService.getMallOrdersPage(orderListDTO));
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody MallOrder mallOrder) {
        if (Objects.isNull(mallOrder.getTotalPrice())
                || Objects.isNull(mallOrder.getOrderId())
                || mallOrder.getOrderId() < 1
                || mallOrder.getTotalPrice() < 1
                || StringUtils.isEmpty(mallOrder.getUserAddress())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.updateOrderInfo(mallOrder);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/order-items/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        List<OrderItemVO> orderItems = mallOrderService.getOrderItems(id);
        if (!CollectionUtils.isEmpty(orderItems)) {
            return ResultGenerator.genSuccessResult(orderItems);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    /**
     * 配货
     */
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    public Result checkDone(@RequestBody Long[] ids) {
        if (checkIdsIsEmpty(ids)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.checkDone(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 出库
     */
    @RequestMapping(value = "/orders/checkOut", method = RequestMethod.POST)
    @ResponseBody
    public Result checkOut(@RequestBody Long[] ids) {
        if (checkIdsIsEmpty(ids)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.checkOut(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/orders/close", method = RequestMethod.POST)
    @ResponseBody
    public Result closeOrder(@RequestBody Long[] ids) {
        if (checkIdsIsEmpty(ids)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallOrderService.closeOrder(ids);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @PostMapping("/orders/{orderNo}/remind")
    @ResponseBody
    public Result remindOrder(@PathVariable("orderId") String orderId) {
        OrderRemind orderRemind = new OrderRemind();
        orderRemind.setOrderId(Long.parseLong(orderId));
        orderRemind.setStatus(1);
        String cancelOrderResult = userRemindService.updateStatus(orderRemind);
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    private boolean checkIdsIsEmpty(Long[] ids) {
        return (ids == null || ids.length < 1);
    }
}