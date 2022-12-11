package com.tian.service;

import com.tian.configs.RedisUtil;
import com.tian.constants.ResultCode;
import com.tian.dao.MallUserMapper;
import com.tian.dto.UserCreditDto;
import com.tian.dto.UserLoginDTO;
import com.tian.entity.MallUser;
import com.tian.enums.ServiceResultEnum;
import com.tian.mq.MQMsgSender;
import com.tian.utils.*;
import com.tian.vo.MallUserVO;
import com.tian.vo.UserListVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@DubboService(version = "1.0.0")
public class MallUserServiceImpl implements MallUserService {

    @Resource
    private MallUserMapper mallUserMapper;


    @DubboReference(version = "1.0.0", check = false)
    private UserCreditDubboService userCreditDubboService;


    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MQMsgSender mqMsgSender;

    @Override
    public PageResult getNewBeeMallUsersPage(UserListVO userListVO) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(userListVO);
        int total = mallUserMapper.getTotalMallUsers(userListVO);
        return new PageResult(mallUsers, total, userListVO.getLimit(), userListVO.getPage());
    }

    @Override
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) > 0) {

            UserCreditDto userCreditDto = new UserCreditDto();
            userCreditDto.setCredit(0);
            userCreditDto.setUserId(registerUser.getUserId());
            //初始化积分
            Result<String> result = userCreditDubboService.initUserCredit(userCreditDto);
            if (result.getResultCode() == ResultGenerator.RESULT_CODE_SUCCESS) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.INIT_CREDIT_ERROR.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public UserLoginDTO login(String loginName, String passwordMD5) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null) {
            if (user.getLockedFlag() == 1) {
                userLoginDTO.setErrorCode(ResultCode.LOGIN_FAILED_LOCK.getErrorCode());
                userLoginDTO.setErrorMsg(ServiceResultEnum.LOGIN_USER_LOCKED.getResult());
                return userLoginDTO;
            }
            //昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            mqMsgSender.sendMsg4LoginLog(user.getUserId().toString(), user.getUserId());
            mqMsgSender.sendMsg4CartItem(user.getUserId().toString(), user.getUserId());
            MallUserVO mallUserVO = new MallUserVO();
            BeanUtil.copyProperties(user, mallUserVO);
            userLoginDTO.setMallUserVO(mallUserVO);
            userLoginDTO.setErrorCode(ResultCode.LOGIN_SUCCESS.getErrorCode());
            userLoginDTO.setErrorMsg(ServiceResultEnum.SUCCESS.getResult());
            return userLoginDTO;
        }
        userLoginDTO.setErrorCode(ResultCode.LOGIN_FAILED.getErrorCode());
        userLoginDTO.setErrorMsg(ServiceResultEnum.LOGIN_ERROR.getResult());
        return userLoginDTO;
    }

    @Override
    public MallUserVO updateUserInfo(MallUser mallUser, Long userId) {
        MallUser userFromDB = mallUserMapper.selectByPrimaryKey(userId);
        if (userFromDB != null) {
            if (!StringUtils.isEmpty(mallUser.getNickName())) {
                userFromDB.setNickName(HappyShoppingMalMallUtils.cleanString(mallUser.getNickName()));
            }
            if (!StringUtils.isEmpty(mallUser.getAddress())) {
                userFromDB.setAddress(HappyShoppingMalMallUtils.cleanString(mallUser.getAddress()));
            }
            if (!StringUtils.isEmpty(mallUser.getIntroduceSign())) {
                userFromDB.setIntroduceSign(HappyShoppingMalMallUtils.cleanString(mallUser.getIntroduceSign()));
            }
            if (mallUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                MallUserVO mallUserVO = new MallUserVO();
                userFromDB = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, mallUserVO);
                return mallUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
