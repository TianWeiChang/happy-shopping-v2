package com.tian.service.impl;

import com.tian.dto.RegisterDto;
import com.tian.dto.UserRegisterDto;
import com.tian.service.UserRegisterDubboService;
import com.tian.service.UserRegisterService;
import com.tian.utils.RedisPrefixConstant;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月10日 16:35
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    private Logger log = LoggerFactory.getLogger(UserRegisterServiceImpl.class);


    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @DubboReference(version = "1.0.0", check = false)
    private UserRegisterDubboService userRegisterDubboService;


    @Override
    public Result checkPhone(String phone) {
        return userRegisterDubboService.checkPhone(phone);
    }

    @Override
    public Result doRegister(UserRegisterDto userRegisterDto) {

        String cacheCode = redisTemplate.opsForValue().get(RedisPrefixConstant.SEND_CODE_KEY_PREFIX + userRegisterDto.getPhone());
        if (StringUtils.isEmpty(cacheCode)) {
            log.error("phone={}, redis中没有对应缓存验证码", userRegisterDto.getPhone());
            return ResultGenerator.genFailResult("注册失败，请发送验证码！");
        }
        //校验验证码是否正确
        if (!cacheCode.equals(userRegisterDto.getCode())) {
            log.error("phone={}, 用户输入验证码={},redis中的验证码={}", userRegisterDto.getPhone(), userRegisterDto.getCode(), cacheCode);
            return ResultGenerator.genFailResult("注册失败，验证码不对！");
        }
        RegisterDto registerDto = new RegisterDto();
        registerDto.setPassword(userRegisterDto.getPassword());
        registerDto.setPhone(userRegisterDto.getPhone());
        return userRegisterDubboService.doRegister(registerDto);
    }
}
