package com.tian.service;

import com.tian.dao.InitUserCreditTmpMapper;
import com.tian.dao.MallUserMapper;
import com.tian.dto.RegisterDto;
import com.tian.entity.InitUserCreditTmp;
import com.tian.entity.MallUser;
import com.tian.mq.MQMsgSender;
import com.tian.utils.MD5Util;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private UserCreditDubboService userCreditDubboService;

    @Resource
    private MallUserMapper mallUserMapper;
    @Resource
    private MQMsgSender mqMsgSender;
    @Resource
    private InitUserCreditTmpMapper initUserCreditTmpMapper;

    @Override
    public Result checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return ResultGenerator.genFailResult("手机号为空");
        }
        return ResultGenerator.genSuccessResult(mallUserMapper.checkPhone(phone));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result doRegister(RegisterDto registerDto) throws Exception {
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(registerDto.getPhone());
        registerUser.setNickName(registerDto.getPhone());
        registerUser.setPasswordMd5(MD5Util.MD5Encode(registerDto.getPassword(), "UTF-8"));
        if (mallUserMapper.insertSelective(registerUser) > 0) {
            InitUserCreditTmp initUserCreditTmp = new InitUserCreditTmp();
            initUserCreditTmp.setUserId(registerUser.getUserId());
            initUserCreditTmp.setStatus(0);
            int flag = initUserCreditTmpMapper.insert(initUserCreditTmp);
            if (flag != 1) {
                throw new Exception("入库本地变量表失败");
            }
            mqMsgSender.sendInitUserCredit(registerUser.getUserId());
            return ResultGenerator.genSuccessResult("注册成功");
        }
        return ResultGenerator.genSuccessResult("注册失败");
    }
}
