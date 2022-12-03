package com.tian.service;


import com.tian.dto.UserLoginDTO;
import com.tian.entity.MallUser;
import com.tian.utils.PageQueryUtil;
import com.tian.utils.PageResult;
import com.tian.vo.MallUserVO;
import com.tian.vo.UserListVO;


public interface MallUserService {
    /**
     * 后台分页
     *
     */
    PageResult getNewBeeMallUsersPage(UserListVO userListVO);

    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);

    /**
     * 登录
     *
     */
    UserLoginDTO login(String loginName, String passwordMD5);

    /**
     * 用户信息修改并返回最新的用户信息
     *
     */
    MallUserVO updateUserInfo(MallUser mallUser, Long userId);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Integer[] ids, int lockStatus);
}
