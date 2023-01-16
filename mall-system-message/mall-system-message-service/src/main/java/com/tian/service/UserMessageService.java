package com.tian.service;

import com.tian.dto.UserMessageQueryReqDto;
import com.tian.dto.UserMessageUpdateReqDto;
import com.tian.entity.UserMessage;

import java.util.List;

/**
 * @author tianwc 公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月16日 15:56
 */
public interface UserMessageService {
    /**
     * 发送站内信
     */
    int add(UserMessage userMessage);

    /**
     * 查询用户站内信列表
     */
    List<UserMessage> findUserMessageList(UserMessageQueryReqDto userMessageQueryReqDto);

    /**
     * 总记录数
     */
    long findUserMessageCount(UserMessageQueryReqDto userMessageQueryReqDto);

    /**
     * 查看站内信内容
     */
    UserMessage findById(Long id);

    /**
     * 修改站内信
     */
    int update(UserMessageUpdateReqDto userMessageUpdateReqDto);
}
