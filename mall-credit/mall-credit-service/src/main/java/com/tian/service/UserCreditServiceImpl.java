package com.tian.service;

import com.tian.dto.AddUserCreditDto;
import com.tian.dto.CreditDetailDto;
import com.tian.dto.ModifyCreditDto;
import com.tian.dto.UserCreditDto;
import com.tian.entity.CreditDetail;
import com.tian.entity.UserCredit;
import com.tian.enums.CreditTypeEnum;
import com.tian.mapper.CreditDetailMapper;
import com.tian.mapper.UserCreditMapper;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年11月27日 11:05
 */
@DubboService(version = "1.0.0")
public class UserCreditServiceImpl implements UserCreditService {

    @Resource
    private UserCreditMapper userCreditMapper;
    @Resource
    private CreditDetailMapper creditDetailMapper;

    @Override
    public Result<AddUserCreditDto> findByUserId(Long userId) {
        UserCredit userCredit = userCreditMapper.selectByUserId(userId);
        if (userCredit != null) {
            AddUserCreditDto addUserCreditDto = new AddUserCreditDto();
            addUserCreditDto.setCredit(userCredit.getCredit());
            addUserCreditDto.setId(userCredit.getId());
            addUserCreditDto.setUserId(userCredit.getUserId());
            return ResultGenerator.genSuccessResult(addUserCreditDto);
        }
        return ResultGenerator.genFailResult("查询用户积分失败");
    }

    @Override
    public Result<String> initUserCredit(UserCreditDto userCreditDto) {
        UserCredit userCredit = userCreditMapper.selectByUserId(userCreditDto.getUserId());
        if (userCredit != null) {
            return ResultGenerator.genSuccessResult("添加成功");
        }
        userCredit = new UserCredit();
        userCredit.setUserId(userCreditDto.getUserId());
        userCredit.setCredit(userCreditDto.getCredit());
        int flag = userCreditMapper.insert(userCredit);
        if (flag == 1) {
            return ResultGenerator.genSuccessResult("添加成功");
        }
        return ResultGenerator.genFailResult("添加失败");
    }

    /**
     * 1、修改积分信息
     * 2、新增明细
     *
     * @param modifyCreditDto 新增积分参数
     * @return 添加成功 返回最新积分
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<UserCreditDto> addCredit(ModifyCreditDto modifyCreditDto) throws Exception {
        UserCredit userCredit = userCreditMapper.selectByUserId(modifyCreditDto.getUserId());
        if (userCredit == null) {
            return ResultGenerator.genFailResult("用户积分添加失败，userId=" + modifyCreditDto.getUserId() + " 有误");
        }
        CreditDetail creditDetail = creditDetailMapper.selectByOrderNo(modifyCreditDto.getOrderNo());
        //重复增加，幂等
        if (creditDetail != null) {
            UserCreditDto userCreditDto = new UserCreditDto();
            userCreditDto.setUserId(modifyCreditDto.getUserId());
            userCreditDto.setCredit(userCredit.getCredit());
            return ResultGenerator.genSuccessResult(userCreditDto);
        }
        userCredit.setCredit(userCredit.getCredit() + modifyCreditDto.getNumber());
        //更新用户积分
        if (checkUpdateCreditSuccess(userCredit)) {
            //新增明细
            if (checkAddCreditDetailSuccess(modifyCreditDto)) {
                throw new Exception("新增积分明细失败");
            }
        }
        UserCreditDto userCreditDto = new UserCreditDto();
        userCreditDto.setUserId(modifyCreditDto.getUserId());
        userCreditDto.setCredit(userCredit.getCredit());
        return ResultGenerator.genSuccessResult(userCreditDto);
    }

    @Override
    public Result reduceCredit(ModifyCreditDto modifyCreditDto) throws Exception {
        UserCredit userCredit = userCreditMapper.selectByUserId(modifyCreditDto.getUserId());
        if (userCredit == null) {
            return ResultGenerator.genFailResult("用户积分扣减失败，userId=" + modifyCreditDto.getUserId() + " 有误");
        }

        CreditDetail creditDetail = creditDetailMapper.selectByOrderNo(modifyCreditDto.getOrderNo());
        //重复扣减，幂等
        if (creditDetail != null) {
            UserCreditDto userCreditDto = new UserCreditDto();
            userCreditDto.setUserId(modifyCreditDto.getUserId());
            userCreditDto.setCredit(userCredit.getCredit());
            return ResultGenerator.genSuccessResult(userCreditDto);
        }

        if (userCredit.getCredit() < modifyCreditDto.getNumber()) {
            return ResultGenerator.genFailResult("用户可用积分不足");
        }

        userCredit.setCredit(userCredit.getCredit() - modifyCreditDto.getNumber());
        //更新用户积分
        if (checkUpdateCreditSuccess(userCredit)) {
            //新增明细
            if (checkAddCreditDetailSuccess(modifyCreditDto)) {
                throw new Exception("新增积分明细失败");
            }
        }
        UserCreditDto userCreditDto = new UserCreditDto();
        userCreditDto.setUserId(modifyCreditDto.getUserId());
        userCreditDto.setCredit(userCredit.getCredit());
        return ResultGenerator.genSuccessResult(userCreditDto);
    }

    private boolean checkUpdateCreditSuccess(UserCredit userCredit) {
        return userCreditMapper.updateByPrimaryKey(userCredit) == 1;
    }

    private boolean checkAddCreditDetailSuccess(ModifyCreditDto modifyCreditDto) {
        CreditDetail creditDetail = new CreditDetail();
        creditDetail.setCreateTime(new Date());
        creditDetail.setNumber(modifyCreditDto.getNumber());
        creditDetail.setUserId(modifyCreditDto.getUserId());
        creditDetail.setType(CreditTypeEnum.ADD.getCode());
        creditDetail.setOrderNo(modifyCreditDto.getOrderNo());
        //明细是否新增成功
        return creditDetailMapper.insert(creditDetail) != 1;
    }

    @Override
    public Result findUserCreditList(Integer userId, Integer start, Integer pageSize) {
        List<CreditDetail> creditDetailList = creditDetailMapper.selectByUserId(userId, start, pageSize);
        if (CollectionUtils.isEmpty(creditDetailList)) {
            return ResultGenerator.genSuccessResult(creditDetailList);
        }

        List<CreditDetailDto> creditDetailDtoList = new ArrayList<>();
        for (CreditDetail creditDetail : creditDetailList) {
            CreditDetailDto creditDetailDto = new CreditDetailDto();
            creditDetailDto.setCreateTime(creditDetail.getCreateTime());
            creditDetailDto.setNumber(creditDetail.getNumber());
            creditDetailDto.setType(creditDetail.getType());
            creditDetailDto.setOrderNo(creditDetail.getOrderNo());
            creditDetailDtoList.add(creditDetailDto);
        }
        return ResultGenerator.genSuccessResult(creditDetailDtoList);
    }
}
