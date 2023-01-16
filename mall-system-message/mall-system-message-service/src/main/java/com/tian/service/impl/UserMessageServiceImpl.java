package com.tian.service.impl;

import com.tian.dto.UserMessageQueryReqDto;
import com.tian.dto.UserMessageUpdateReqDto;
import com.tian.entity.UserMessage;
import com.tian.mapper.UserMessageMapper;
import com.tian.service.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月16日 16:09
 */
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private Logger logger = LoggerFactory.getLogger(UserMessageServiceImpl.class);

    @Resource
    private UserMessageMapper userMessageMapper;

    @Override
    public int add(UserMessage userMessage) {
        return userMessageMapper.insert(userMessage);
    }

    @Override
    public List<UserMessage> findUserMessageList(UserMessageQueryReqDto userMessageQueryReqDto) {
        return userMessageMapper.findUserMessageList(userMessageQueryReqDto);
    }

    @Override
    public long findUserMessageCount(UserMessageQueryReqDto userMessageQueryReqDto) {
        return userMessageMapper.findUserMessageCount(userMessageQueryReqDto);
    }

    @Override
    public UserMessage findById(Long id) {
        return userMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(UserMessageUpdateReqDto userMessageUpdateReqDto) {
        if (userMessageUpdateReqDto == null) {
            logger.error("UserMessage update parameter is null!");
            return 0;
        }
        if (userMessageUpdateReqDto.getId() == null || userMessageUpdateReqDto.getId() == 0) {
            logger.error("UserMessage update parameter id is null!");
            return 0;
        }
        UserMessage userMessage = userMessageMapper.selectByPrimaryKey(userMessageUpdateReqDto.getId());
        if (userMessage == null) {
            logger.error("UserMessage update parameter id is error!");
            return 0;
        }
        if (userMessageUpdateReqDto.getDelete() != null) {
            userMessage.setDeleted(userMessageUpdateReqDto.getDelete());
        }
        if (userMessageUpdateReqDto.getRead() != null) {
            userMessage.setRead(userMessageUpdateReqDto.getRead());
        }
        return userMessageMapper.updateByPrimaryKey(userMessage);
    }
}
