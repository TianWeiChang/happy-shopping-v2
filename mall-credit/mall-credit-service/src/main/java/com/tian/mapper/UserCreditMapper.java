package com.tian.mapper;

import com.tian.entity.UserCredit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCreditMapper {
    int insert(UserCredit record);

    UserCredit selectByPrimaryKey(Long id);

    UserCredit selectByUserId(Long userId);

    int updateByPrimaryKey(UserCredit record);
}