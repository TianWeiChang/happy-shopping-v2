package com.tian.service;

import com.tian.dao.MallOrderMapper;
import com.tian.dao.OrderRemindMapper;
import com.tian.entity.MallOrder;
import com.tian.entity.OrderRemind;
import com.tian.enums.ServiceResultEnum;
import com.tian.utils.PageResult;
import com.tian.utils.PageUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@DubboService(version = "1.0.0")
public class UserRemindServiceImpl implements UserRemindService {

    @Resource
    private OrderRemindMapper orderRemindMapper;

    @Resource
    private MallOrderMapper mallOrderMapper;


    @Override
    public String remind(String orderNo, Long userId, String userName) {
        MallOrder mallOrder = mallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(mallOrder.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            OrderRemind orderRemind = new OrderRemind();
            orderRemind.setOrderId(mallOrder.getOrderId());
            orderRemind.setUserId(userId);
            orderRemind.setUserName(userName);
            orderRemind.setCreateTime(new Date());
            orderRemind.setUpdateTime(new Date());
            int flag = orderRemindMapper.insert(orderRemind);
            if (flag > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
        }
        return ServiceResultEnum.OPERATE_ERROR.getResult();
    }

    @Override
    public OrderRemind selectByUserId(Long userId, Long orderId) {
        return orderRemindMapper.selectByUserId(userId, orderId);
    }

    @Override
    public PageResult findRemindList(PageUtil pageUtil) {
        List<OrderRemind> orderRemindList = orderRemindMapper.findRemindList(pageUtil);
        int total = orderRemindMapper.findCount();
        return new PageResult(orderRemindList, total, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public String updateStatus(OrderRemind orderRemind) {
        int flag = orderRemindMapper.updateStatus(orderRemind);
        if (flag > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.ERROR.getResult();
    }

    @Override
    public Boolean updateBatchStatus(List<Long> idList, Integer status) {
        return orderRemindMapper.updateBatchStatus(idList, status) > 0;
    }

    @Override
    public Integer findCount() {
        return orderRemindMapper.findCount();
    }

}