package com.tian.service;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月08日 20:49
 * <p>
 * 发送验证码
 */
public interface SendCodeService {
    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return 是否发送成功
     */
    boolean sendCode(String phone);
}
