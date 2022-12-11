package com.tian.service;

import com.tian.dto.AddUserCreditDto;
import com.tian.dto.ModifyCreditDto;
import com.tian.dto.UserCreditDto;
import com.tian.utils.Result;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月11日 10:00
 */
public interface UserCreditService {
    /**
     * 用户积分查询
     *
     * @param userId 用户id
     * @return 用户积分信息
     */
    Result<AddUserCreditDto> findByUserId(Long userId);

    /**
     * 新增用户积分信息
     *
     * @param userCreditDto 用户积分信息
     * @return 添加成功或失败
     */
    Result<String> initUserCredit(UserCreditDto userCreditDto);

    /**
     * 增加用户积分
     *
     * @param modifyCreditDto 新增积分参数
     * @return 增加后用户当前积分信息
     * @throws Exception 添加积分异常信息
     */
    Result<UserCreditDto> addCredit(ModifyCreditDto modifyCreditDto) throws Exception;

    /**
     * 扣减用户积分
     *
     * @param modifyCreditDto 扣减积分参数
     * @return 扣减后用户当前积分信息
     * @throws Exception 添加积分异常信息
     */
    Result<UserCreditDto> reduceCredit(ModifyCreditDto modifyCreditDto) throws Exception;

    /**
     * 获取用户积分明细
     *
     * @param userId   用户id
     * @param start    分页起始
     * @param pageSize 分页大小
     * @return 积分明细
     */
    Result findUserCreditList(Integer userId, Integer start, Integer pageSize);

    /**
     * 预扣减积分
     *
     * @param modifyCreditDto 扣减积分参数
     * @return 是否成功
     */
    Result preReduceCredit(ModifyCreditDto modifyCreditDto) throws Exception;

    /**
     * 扣减积分
     *
     * @param modifyCreditDto 扣减积分参数
     * @return 是否成功
     */
    Result doReduceCredit(ModifyCreditDto modifyCreditDto);
}
