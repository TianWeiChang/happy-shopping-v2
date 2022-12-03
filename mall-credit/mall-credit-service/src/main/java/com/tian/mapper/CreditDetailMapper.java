package com.tian.mapper;

import com.tian.entity.CreditDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CreditDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreditDetail record);

    CreditDetail selectByPrimaryKey(Integer id);

    CreditDetail selectByOrderNo(String orderNo);

    List<CreditDetail> selectByUserId(@Param("userId") Integer userId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int updateByPrimaryKey(CreditDetail record);
}