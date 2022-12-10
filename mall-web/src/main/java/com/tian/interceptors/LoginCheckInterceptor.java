/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tian.interceptors;

import com.tian.configs.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户端session校验拦截器
 *
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
       /* HttpSession session = request.getSession();
        if (null == request.getSession().getAttribute("loginUserId")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        } else {
            try {
                //验证当前请求的session是否是已登录的session
                long userId = (long) session.getAttribute("loginUserId");
                Object loginSessionId = redisUtil.get("loginUser" + userId);
                if (loginSessionId != null && loginSessionId.toString().equals(session.getId())) {
                    logger.info("用户登录成功，userId={}", userId);
                    return true;
                }
                logger.info("用户登录失败，userId={}", userId);
            } catch (Exception e) {
                logger.info("用户登录,sessionId={}", session.getId());
            }
            rturn false;e
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
