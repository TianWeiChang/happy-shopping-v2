package com.tian.dubbo;

import com.alibaba.fastjson.JSON;
import com.tian.config.RedisDistributedLock;
import com.tian.constants.RedisCacheKey;
import com.tian.dto.UserMessageAddDto;
import com.tian.dto.UserMessageQueryReqDto;
import com.tian.dto.UserMessageUpdateReqDto;
import com.tian.entity.SystemMessage;
import com.tian.entity.UserMessage;
import com.tian.service.SystemMessageService;
import com.tian.service.UserMessageDubboService;
import com.tian.service.UserMessageService;
import com.tian.util.UserMessageConvert;
import com.tian.utils.*;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月16日 16:18
 * <p>
 * 用户站内信 操作
 * TODO 留点功能给大家做：用户点全部已读、删除某条站内信、批量删除站内信
 */
@DubboService(version = "1.0.0")
public class UserMessageDubboServiceImpl implements UserMessageDubboService {

    @Resource
    private UserMessageService userMessageService;
    @Resource
    private SystemMessageService systemMessageService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RedisDistributedLock redisDistributedLock;
    private final static String KEY_PRE = "user_message";

    @Override
    public Result addUserMessage(UserMessageAddDto userMessageAddDto) {
        if (userMessageAddDto == null) {
            return ResultGenerator.genFailResult("参数为空");
        }
        if (userMessageAddDto.getUserId() == null || StringUtils.isEmpty(userMessageAddDto.getUserName())) {
            return ResultGenerator.genFailResult("消息接受者参数为空");
        }
        if (userMessageAddDto.getMessageId() == null || userMessageAddDto.getMessageId() == 0) {
            return ResultGenerator.genFailResult("消息id参数为空");
        }

        //缓存中不存在，大量用户都同时使用这个站内信模板
        Result checkResult = checkMessage(userMessageAddDto);
        if (checkResult != null) {
            return checkResult;
        }
        UserMessage userMessage = new UserMessage();
        userMessage.setRead(0);
        userMessage.setReceiverId(userMessageAddDto.getUserId());
        userMessage.setMessageId(userMessageAddDto.getMessageId());
        userMessage.setDeleted(0);
        userMessage.setMessageContent(userMessageAddDto.getMessageContent());
        Date date = new Date();
        userMessage.setCreateTime(date);
        userMessage.setUpdateTime(date);
        userMessage.setReceiverName(userMessageAddDto.getUserName());
        int flag = userMessageService.add(userMessage);
        if (flag == 1) {
            return ResultGenerator.genSuccessResult("发送成功");
        }
        return ResultGenerator.genFailResult("发送失败");
    }

    private Result checkMessage(UserMessageAddDto userMessageAddDto) {
        //检查模板是否存在
        String cache = redisTemplate.opsForValue().get(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + userMessageAddDto.getMessageId());
        SystemMessage systemMessage;
        if (StringUtils.isEmpty(cache)) {
            //缓存击穿
            boolean lock = redisDistributedLock.lock(KEY_PRE + userMessageAddDto.getMessageId());
            try {
                if (lock) {
                    cache = redisTemplate.opsForValue().get(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + userMessageAddDto.getMessageId());
                    //再次检查
                    if (StringUtils.isEmpty(cache)) {
                        systemMessage = systemMessageService.selectByPrimaryKey(userMessageAddDto.getMessageId());
                        if (systemMessage == null) {
                            //缓存穿透
                            redisTemplate.opsForValue().set(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + userMessageAddDto.getMessageId(), JSON.toJSONString(new SystemMessage()));
                            return ResultGenerator.genFailResult("模板不存在");
                        }
                        redisTemplate.opsForValue().set(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + userMessageAddDto.getMessageId(), JSON.toJSONString(systemMessage));
                    }
                }
            }finally {
                redisDistributedLock.unlock(KEY_PRE + userMessageAddDto.getMessageId());
            }
        }
        return null;
    }

    @Override
    public PageResult findUserMessageList(UserMessageQueryReqDto userMessageQueryReqDto) {
        long count = userMessageService.findUserMessageCount(userMessageQueryReqDto);
        if (count == 0) {
            return new PageResult(new ArrayList<>(), 0, userMessageQueryReqDto.getPageSize(), userMessageQueryReqDto.getCurrentPage());
        }
        List<UserMessage> list = userMessageService.findUserMessageList(userMessageQueryReqDto);
        return new PageResult(UserMessageConvert.INSTANCE.convertList(list), 0, userMessageQueryReqDto.getPageSize(), userMessageQueryReqDto.getCurrentPage());
    }

    @Override
    public Result findById(Long id) {
        UserMessage userMessage = userMessageService.findById(id);
        if (userMessage == null) {
            return ResultGenerator.genSuccessResult(null);
        }
        return ResultGenerator.genSuccessResult(UserMessageConvert.INSTANCE.convert(userMessage));
    }

    @Override
    public Result update(UserMessageUpdateReqDto userMessageQueryReqDto) {
        int flag = userMessageService.update(userMessageQueryReqDto);
        if (flag == 1) {
            return ResultGenerator.genSuccessResult("修改成功");
        }
        return ResultGenerator.genFailResult("修改失败");
    }
}
