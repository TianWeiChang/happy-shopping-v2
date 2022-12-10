package com.tian.controller;

import com.tian.dto.RegisterDto;
import com.tian.dto.UserRegisterDto;
import com.tian.service.UserRegisterDubboService;
import com.tian.service.UserRegisterService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月08日 16:32
 */
@RestController
@RequestMapping("/register")
public class UserRegisterController {

    @Resource
    private UserRegisterService userRegisterService;

    @GetMapping("/check/phone")
    public Result checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return ResultGenerator.genFailResult("手机号为空!");
        }
        if (phone.length() != 11) {
            return ResultGenerator.genFailResult("请输入正确手机号码!");
        }
        Result result = userRegisterService.checkPhone(phone);
        //表示我们能注册了，因为大部分都是没有注册的，真正注册过的人少部分来操作这个接口
        if (result.getResultCode() == ResultGenerator.RESULT_CODE_SUCCESS
                && Integer.parseInt(result.getData().toString()) == 0) {
            return ResultGenerator.genSuccessResult("可以继续注册");
        }
        return ResultGenerator.genFailResult("该手机号已注册过，请直接登录");
    }

    @PostMapping("/do")
    public Result doRegister(@RequestBody UserRegisterDto userRegisterDto) {
        if (StringUtils.isEmpty(userRegisterDto.getCode()) || StringUtils.isEmpty(userRegisterDto.getPhone())) {
            return ResultGenerator.genFailResult("参数为空!");
        }
        return userRegisterService.doRegister(userRegisterDto);
    }
}
