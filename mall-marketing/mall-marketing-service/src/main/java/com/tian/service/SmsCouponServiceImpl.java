package com.tian.service;

import com.tian.dto.SmsCouponDto;
import com.tian.utils.Result;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月02日 20:04
 * <p>
 * 优惠券
 */
@DubboService(version = "1.0.0")
public class SmsCouponServiceImpl implements SmsCouponService {

    @Override
    public Result deleteByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public Result insert(SmsCouponDto record) {
        return null;
    }

    @Override
    public Result selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public Result selectAll() {
        return null;
    }

    @Override
    public Result updateByPrimaryKey(SmsCouponDto record) {
        return null;
    }
}
