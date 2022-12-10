package com.tian.service;

import com.tian.dao.MallUserMapper;
import com.tian.dto.RegisterDto;
import com.tian.dto.UserCreditDto;
import com.tian.entity.MallUser;
import com.tian.utils.MD5Util;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月08日 16:39
 * <p>
 * 用户注册
 * 1、检查用熟手机号是否已经注册
 * 2、用户注册
 */
@DubboService(version = "1.0.0")
public class UserRegisterDubboServiceImpl implements UserRegisterDubboService {


    @DubboReference(version = "1.0.0", check = false)
    private UserCreditService userCreditService;

    @Resource
    private MallUserMapper mallUserMapper;

    @Override
    public Result checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return ResultGenerator.genFailResult("手机号为空");
        }
        return ResultGenerator.genSuccessResult(mallUserMapper.checkPhone(phone));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result doRegister(RegisterDto registerDto) {
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(registerDto.getPhone());
        registerUser.setNickName(registerDto.getPhone());
        registerUser.setPasswordMd5(MD5Util.MD5Encode(registerDto.getPassword(), "UTF-8"));
        if (mallUserMapper.insertSelective(registerUser) > 0) {

            /*
             * 此时就涉及到分布式事务了，也就是说 Transactional 注解是没用了。
             * UserCreditDto userCreditDto = new UserCreditDto();
             * userCreditDto.setCredit(0);
             *  userCreditDto.setUserId(registerUser.getUserId());
             * //初始化积分
             * Result<String> result = userCreditService.initUserCredit(userCreditDto);
             *
             *
             *  if (result.getResultCode() == ResultGenerator.RESULT_CODE_SUCCESS) {
             *    return ResultGenerator.genSuccessResult("注册成功");
             * }
             **/

            //为了让架构没那么复杂，我们来使用本地表做

            /**
             * 思路：
             * 1、创建一张临时表：init_user_credit_tmp(id,userId,status(未创建、已创建))
             * 2、发送消息到MQ中，消费者去拉取消息，然后初始化用户积分
             * 3、另外，启动一个单独定时任务去处理消息用户
             */
            return ResultGenerator.genSuccessResult("注册成功");

        }
        return ResultGenerator.genSuccessResult("注册失败");
    }
}
