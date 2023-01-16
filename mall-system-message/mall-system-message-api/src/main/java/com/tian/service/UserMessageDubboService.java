package com.tian.service;

import com.tian.dto.UserMessageAddDto;
import com.tian.dto.UserMessageDto;
import com.tian.dto.UserMessageQueryReqDto;
import com.tian.dto.UserMessageUpdateReqDto;
import com.tian.utils.PageResult;
import com.tian.utils.Result;

import java.util.List;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月16日 16:18
 */
public interface UserMessageDubboService {
    /**
     * 发送站内信
     */
    Result addUserMessage(UserMessageAddDto userMessageAddDto);

    /**
     * 查询用户站内信列表
     */
    PageResult findUserMessageList(UserMessageQueryReqDto userMessageQueryReqDto);

    /**
     * 查看站内信内容
     */
    Result findById(Long id);

    /**
     * 修改站内信
     */
    Result update(UserMessageUpdateReqDto userMessageQueryReqDto);
}
