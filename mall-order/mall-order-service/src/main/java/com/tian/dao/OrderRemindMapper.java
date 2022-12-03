package com.tian.dao;

import com.tian.entity.OrderRemind;
import com.tian.utils.PageUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderRemindMapper {
    int insert(OrderRemind orderRemind);

    OrderRemind selectByUserId(@Param("userId") Long userId, @Param("orderId") Long orderId);

    List<OrderRemind> findRemindList(PageUtil pageUtil);

    int updateStatus(OrderRemind orderRemind);

    int updateBatchStatus(@Param("orderIds") List<Long> orderIds, @Param("status") int status);

    int findCount();
}