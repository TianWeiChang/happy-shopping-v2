package com.tian.service;

import com.tian.entity.OrderRemind;
import com.tian.utils.PageResult;
import com.tian.utils.PageUtil;

import java.util.List;

public interface UserRemindService {
    String remind(String orderNo, Long userId, String userName);

    OrderRemind selectByUserId(Long userId, Long orderId);

    PageResult findRemindList(PageUtil pageUtil);

    String updateStatus(OrderRemind userPoints);

    Boolean updateBatchStatus(List<Long> idList, Integer status);

    Integer findCount();
}
