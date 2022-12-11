package com.tian.service;

import com.tian.dto.RegisterDto;
import com.tian.utils.Result;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月08日 16:37
 */
public interface UserRegisterDubboService {
    /**
     * 检查手机号是否已经存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    Result checkPhone(String phone);

    /**
     * 用户注册
     *
     * @param registerDto 注册信息
     * @return 是否注册成功
     */
    Result doRegister(RegisterDto registerDto) throws Exception;
}
