package com.tian.mapper;

import com.tian.dto.UserMessageQueryReqDto;
import com.tian.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserMessage record);

    UserMessage selectByPrimaryKey(Long id);

    List<UserMessage> findUserMessageList(UserMessageQueryReqDto userMessageQueryReqDto);

    long findUserMessageCount(UserMessageQueryReqDto userMessageQueryReqDto);

    int updateByPrimaryKey(UserMessage record);
}