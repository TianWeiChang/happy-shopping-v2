package com.tian.mq;

import com.tian.configs.RabbitmqConfig;
import com.tian.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月09日 11:06
 */
@Component
public class BasicPublisher {
    /*private Logger log = LoggerFactory.getLogger(BasicPublisher.class);
    @Resource
    private RabbitTemplate rabbitTemplate;


    // 发送字符串类型的消息
    public void sendCodeMsgPublisher(String messageStr) {
        if (!StringUtils.isEmpty(messageStr)) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(RabbitMQConstants.FANOUT_EXCHANGE);
                rabbitTemplate.setRoutingKey(RabbitMQConstants.SEND_CODE_ROUTING_KEY);
                rabbitTemplate.setDefaultReceiveQueue(RabbitMQConstants.SEND_CODE);

                // 2创建队列、交换机、消息 设置持久化模式
                // 设置消息的持久化模式
                Message message = MessageBuilder.withBody(messageStr.getBytes("utf-8")).
                        setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(message);
                log.info("基本消息模型-生产者-发送消息：{}", messageStr);

            } catch (UnsupportedEncodingException e) {
                log.error("基本消息模型-生产者-发送消息发生异常：{}", messageStr, e.fillInStackTrace());
            }
        }
    }*/

}