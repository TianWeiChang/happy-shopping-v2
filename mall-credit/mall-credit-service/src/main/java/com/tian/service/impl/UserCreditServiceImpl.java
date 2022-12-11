package com.tian.service.impl;

import com.tian.config.RedisDistributedLockUtil;
import com.tian.config.RedisUtil;
import com.tian.dto.AddUserCreditDto;
import com.tian.dto.CreditDetailDto;
import com.tian.dto.ModifyCreditDto;
import com.tian.dto.UserCreditDto;
import com.tian.entity.CreditDetail;
import com.tian.entity.UserCredit;
import com.tian.enums.CreditTypeEnum;
import com.tian.mapper.CreditDetailMapper;
import com.tian.mapper.UserCreditMapper;
import com.tian.service.UserCreditService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年11月27日 11:05
 */
@Service
public class UserCreditServiceImpl implements UserCreditService {

    private Logger logger = LoggerFactory.getLogger(UserCreditServiceImpl.class);

    private static final String LOCK_KEY_PRE = "CREDIT_LOCK_";

    @Resource
    private UserCreditMapper userCreditMapper;
    @Resource
    private CreditDetailMapper creditDetailMapper;

    @Resource
    private RedisDistributedLockUtil redisDistributedLockUtil;

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

        String lockKey = LOCK_KEY_PRE + modifyCreditDto.getUserId();
        String clientId = UUID.randomUUID().toString();
        Boolean lock = redisDistributedLockUtil.tryLock(lockKey, clientId, 5000L);

        try {
            //判断是否获取分布式锁
            if (!lock) {
                return ResultGenerator.genFailResult("用户积分添加失败，userId=" + modifyCreditDto.getUserId() + " 获取分布式锁失败");
            }

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
        } finally {
            //分布式锁释放
            redisDistributedLockUtil.unLock(lockKey, clientId);
        }
    }

    @Override
    public Result reduceCredit(ModifyCreditDto modifyCreditDto) throws Exception {
        String lockKey = LOCK_KEY_PRE + modifyCreditDto.getUserId();
        String clientId = UUID.randomUUID().toString();
        Boolean lock = redisDistributedLockUtil.tryLock(lockKey, clientId, 5000L);

        //判断是否获取分布式锁
        if (!lock) {
            return ResultGenerator.genFailResult("用户积分添加失败，userId=" + modifyCreditDto.getUserId() + " 获取分布式锁失败");
        }
        try {

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
        } finally {
            //分布式锁释放
            redisDistributedLockUtil.unLock(lockKey, clientId);
        }
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result preReduceCredit(ModifyCreditDto modifyCreditDto) throws Exception {

        String lockKey = LOCK_KEY_PRE + modifyCreditDto.getUserId();
        String clientId = UUID.randomUUID().toString();
        Boolean lock = redisDistributedLockUtil.tryLock(lockKey, clientId, 5000L);

        //判断是否获取分布式锁
        if (!lock) {
            return ResultGenerator.genFailResult("用户与扣减积分失败，userId=" + modifyCreditDto.getUserId() + " 获取分布式锁失败");
        }
        try {

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

            userCredit.setPreCredit(modifyCreditDto.getNumber());
            //与扣减
            int flag = userCreditMapper.updateByUserId(userCredit);
            if (flag != 1) {
                return ResultGenerator.genFailResult("用户积分预扣减失败，userId=" + modifyCreditDto.getUserId());
            }
            CreditDetail creditDetail1 = new CreditDetail();
            creditDetail1.setUserId(modifyCreditDto.getUserId());
            creditDetail1.setType(CreditTypeEnum.PRE_REDUCE.getCode());
            creditDetail1.setOrderNo(modifyCreditDto.getOrderNo());
            creditDetail1.setNumber(modifyCreditDto.getNumber());
            creditDetail1.setCreateTime(new Date());
            if (creditDetailMapper.insert(creditDetail1) != 1) {
                throw new Exception("增加明细失败");
            }

            UserCreditDto userCreditDto = new UserCreditDto();
            userCreditDto.setUserId(modifyCreditDto.getUserId());
            userCreditDto.setCredit(userCredit.getCredit());
            return ResultGenerator.genSuccessResult(userCreditDto);

        } finally {
            //分布式锁释放
            redisDistributedLockUtil.unLock(lockKey, clientId);
        }
    }

    @Override
    public Result doReduceCredit(ModifyCreditDto modifyCreditDto) {
        String lockKey = LOCK_KEY_PRE + modifyCreditDto.getUserId();
        String clientId = UUID.randomUUID().toString();
        Boolean lock = redisDistributedLockUtil.tryLock(lockKey, clientId, 5000L);

        //判断是否获取分布式锁
        if (!lock) {
            return ResultGenerator.genFailResult("扣减用户积分失败，userId=" + modifyCreditDto.getUserId() + " 获取分布式锁失败");
        }
        try {

            UserCredit userCredit = userCreditMapper.selectByUserId(modifyCreditDto.getUserId());
            if (userCredit == null) {
                return ResultGenerator.genFailResult("用户积分扣减失败，userId=" + modifyCreditDto.getUserId() + " 有误");
            }
            CreditDetail creditDetail = creditDetailMapper.selectByOrderNo(modifyCreditDto.getOrderNo());
            if (creditDetail == null) {
                return ResultGenerator.genFailResult("用户积分扣减失败，userId=" + modifyCreditDto.getUserId() + ",orderNo=modifyCreditDto.getOrderNo()" + "不存在");
            }

            //已扣减成功，幂等 防重
            if (userCredit.getPreCredit() == 0) {
                UserCreditDto userCreditDto = new UserCreditDto();
                userCreditDto.setUserId(modifyCreditDto.getUserId());
                userCreditDto.setCredit(userCredit.getCredit());
                return ResultGenerator.genSuccessResult(userCreditDto);
            }
            userCredit.setCredit(userCredit.getCredit() + userCredit.getPreCredit());
            userCredit.setPreCredit(0);
            int flag = userCreditMapper.updateByUserId(userCredit);

            if (flag != 1) {
                return ResultGenerator.genFailResult("用户积分扣减失败，userId=" + modifyCreditDto.getUserId());
            }
            creditDetail.setType(CreditTypeEnum.REDUCE.getCode());
            if (creditDetailMapper.updateByPrimaryKey(creditDetail) != 1) {
                logger.error("积分扣减明细type 修改失败，userId=" + modifyCreditDto.getUserId() + ",orderNo=" + modifyCreditDto.getOrderNo());
            }
            UserCreditDto userCreditDto = new UserCreditDto();
            userCreditDto.setUserId(modifyCreditDto.getUserId());
            userCreditDto.setCredit(userCredit.getCredit());
            return ResultGenerator.genSuccessResult(userCreditDto);
        } finally {
            //分布式锁释放
            redisDistributedLockUtil.unLock(lockKey, clientId);
        }
    }
}
