package com.tian.dao;

import com.tian.entity.InitUserCreditTmp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InitUserCreditTmpMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InitUserCreditTmp record);

    InitUserCreditTmp selectByPrimaryKey(Long id);

    List<InitUserCreditTmp> selectAll();

    Integer count();

    List<InitUserCreditTmp> selectPage(@Param("start") int start, @Param("pageSize") int pageSize);

    int updateByPrimaryKey(InitUserCreditTmp record);
}