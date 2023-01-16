package com.tian.receiver;

import com.tian.message.UserMessageAddMessage;
import com.tian.mq.RabbitMQConstants;
import com.tian.service.UserMessageDubboService;
import com.tian.util.UserMessageConvert;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月16日 19:42
 * 用户发站内信 消息消费
 */
@Component
public class UserMessageMsgReceiver {
    private Logger logger = LoggerFactory.getLogger(UserMessageMsgReceiver.class);

    @DubboReference(version = "1.0.0")
    private UserMessageDubboService userMessageDubboService;

    @RabbitListener(queues = RabbitMQConstants.USER_MESSAGE_QUEUE)
    @RabbitHandler
    public void process(UserMessageAddMessage messageAddMessage) {
        if (messageAddMessage == null) {
            logger.error("msg is null");
        }
        userMessageDubboService.addUserMessage(UserMessageConvert.INSTANCE.convert(messageAddMessage));
    }
}
