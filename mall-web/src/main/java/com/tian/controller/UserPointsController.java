package com.tian.controller;

import com.tian.service.UserPointsService;
import com.tian.utils.UserSessionUtil;
import com.tian.vo.MallUserVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 我的积分
 */
@Controller
public class UserPointsController {

    @DubboReference(version = "1.0.0", check = false)
    private UserPointsService userPointsService;

    /**
     * 获取当前用户的积分
     */
    @GetMapping("/points")
    public String getUserPoints(HttpServletRequest request, HttpSession httpSession) {
        MallUserVO user = UserSessionUtil.userSession(httpSession);
        request.setAttribute("userPoints", userPointsService.selectByUserId(user.getUserId()));
        request.setAttribute("path", "points");
        return "mall/my-points";
    }
}
