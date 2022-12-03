package com.tian.service;

import com.tian.dto.SmsCouponDto;
import com.tian.utils.Result;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月02日 20:03
 */
public interface SmsCouponService {
    Result deleteByPrimaryKey(Long id);

    Result insert(SmsCouponDto record);

    Result selectByPrimaryKey(Long id);

    Result selectAll();

    Result updateByPrimaryKey(SmsCouponDto record);
}
