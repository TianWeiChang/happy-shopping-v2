package com.tian.dubbo;

import com.tian.dto.AddUserCreditDto;
import com.tian.dto.ModifyCreditDto;
import com.tian.dto.UserCreditDto;
import com.tian.service.UserCreditDubboService;
import com.tian.service.UserCreditService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月11日 10:00
 */
@DubboService(version = "1.0.0")
public class UserCreditDubboServiceImpl implements UserCreditDubboService {

    @Resource
    private UserCreditService userCreditService;


    @Override
    public Result<AddUserCreditDto> findByUserId(Long userId) {
        return userCreditService.findByUserId(userId);
    }

    @Override
    public Result<String> initUserCredit(UserCreditDto userCreditDto) {
        return userCreditService.initUserCredit(userCreditDto);
    }

    @Override
    public Result addCredit(ModifyCreditDto modifyCreditDto) {
        try {
            return userCreditService.addCredit(modifyCreditDto);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("用户积分添加失败，userId=" + modifyCreditDto.getUserId());
        }
    }

    @Override
    public Result reduceCredit(ModifyCreditDto modifyCreditDto) {
        try {
            return userCreditService.reduceCredit(modifyCreditDto);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("用户积分扣减失败，userId=" + modifyCreditDto.getUserId());
        }
    }

    @Override
    public Result findUserCreditList(Integer userId, Integer start, Integer pageSize) {
        return null;
    }

    @Override
    public Result preReduceCredit(ModifyCreditDto modifyCreditDto) {
        try {
            return userCreditService.preReduceCredit(modifyCreditDto);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("用户积分预扣减失败，userId=" + modifyCreditDto.getUserId());
        }
    }

    @Override
    public Result doReduceCredit(ModifyCreditDto modifyCreditDto) {
        try {
            return userCreditService.preReduceCredit(modifyCreditDto);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("用户积分扣减失败，userId=" + modifyCreditDto.getUserId());
        }
    }
}
