package com.tian.controller;

import com.tian.service.SendCodeService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.utils.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月08日 17:19
 */
@RestController
@RequestMapping
public class SendCodeController {

    @Resource
    private SendCodeService sendCodeService;

    @GetMapping("/send/code")
    public Result sendCode(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return ResultGenerator.genFailResult("手机号为空!");
        }
        if (phone.length() != 11) {
            return ResultGenerator.genFailResult("请输入正确手机号码!");
        }
        String code = "123456";
        //
        sendCodeService.sendCode(phone);
        return ResultGenerator.genSuccessResult("发送成功");
    }
}
