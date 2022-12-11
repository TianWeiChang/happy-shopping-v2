package com.tian.service;

import com.tian.utils.Result;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月11日 17:40
 */
public interface InitUserCreditTmpDubboService {

    /**
     * 初始化用户积分账户
     */
    void initUserCredit();

    Result addInitUserCredit(Long userId);
}
