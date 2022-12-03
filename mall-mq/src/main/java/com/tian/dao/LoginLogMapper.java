package com.tian.dao;

import com.tian.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公众号：Java后端技术全栈
 *
 * @author 田哥
 */
@Mapper
public interface LoginLogMapper {
    int insertOne(LoginLog loginLog);

    List<LoginLog> selectByUserId(@Param("userId") Integer userId);
}