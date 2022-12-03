package com.tian.controller;

import cn.hutool.captcha.ShearCaptcha;
import com.tian.configs.RedisUtil;
import com.tian.constants.Constants;
import com.tian.constants.RedisCacheKey;
import com.tian.constants.ResultCode;
import com.tian.dto.UserLoginDTO;
import com.tian.entity.MallUser;
import com.tian.enums.ServiceResultEnum;
import com.tian.service.MallUserService;
import com.tian.utils.MD5Util;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.vo.MallUserVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @DubboReference(version = "1.0.0", check = false)
    private MallUserService mallUserService;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request) {
        request.setAttribute("path", "personal");
        return "mall/personal";
    }

    //退出删除用户相关信息
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        MallUserVO user = (MallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        httpSession.removeAttribute("loginUserId");
        redisUtil.remove("loginUser" + user.getUserId());
        return "mall/login";
    }

    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mall/login";
    }

    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        ShearCaptcha shearCaptcha = (ShearCaptcha) httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);

        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        UserLoginDTO loginResult = mallUserService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"));
        //登录成功
        if (loginResult.getErrorCode() == ResultCode.LOGIN_SUCCESS.getErrorCode()) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            MallUserVO mallUserVO = loginResult.getMallUserVO();
            httpSession.setAttribute("loginUserId", mallUserVO.getUserId());
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, mallUserVO);
            httpSession.setAttribute("newBeeMallUser", mallUserVO);
            redisUtil.set("loginUser" + mallUserVO.getUserId(), httpSession.getId());

            Object count = redisUtil.get(RedisCacheKey.USER_CART_COUNT_KEY + mallUserVO.getUserId());
            httpSession.setAttribute("cartCount", count == null ? 0 : (int) count);
            return ResultGenerator.genSuccessResult();
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult.getErrorMsg());
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        ShearCaptcha shearCaptcha = (ShearCaptcha) httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);
        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String registerResult = mallUserService.register(loginName, password);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        MallUserVO mallUserTemp = mallUserService.updateUserInfo(mallUser, mallUser.getUserId());
        if (mallUserTemp == null) {
            return ResultGenerator.genFailResult("修改失败");
        } else {
            //返回成功
            return ResultGenerator.genSuccessResult();
        }
    }
}
