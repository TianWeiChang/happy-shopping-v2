package com.tian.mapper;

import com.tian.entity.SystemMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMessage record);

    SystemMessage selectByPrimaryKey(Long id);

    List<SystemMessage> selectAll();

    int updateByPrimaryKey(SystemMessage record);

    int deleteByPrimaryKey(SystemMessage record);
}