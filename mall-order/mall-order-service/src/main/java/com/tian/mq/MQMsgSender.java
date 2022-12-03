package com.tian.mq;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MQMsgSender {

    private Logger logger = LogManager.getLogger(MQMsgSender.class);

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Resource
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
        // 设置生产者消息确认
        rabbitTemplate.setConfirmCallback(new RabbitConfirmCallback());
    }

    /**
     * 发送登录日志
     */
    public void sendMsg4LoginLog(String messageId, Object messageData) {
        logger.info("send login log msg：{}", JSON.toJSONString(messageData));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, RabbitMQConstants.LOGIN_LOG_ROUTING_KEY, map, correlationData);
        logger.info("sent success：{}", JSON.toJSONString(messageId));
    }

    public void sendProductAdd(Integer productId, Integer addCount) {

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", productId);
        map.put("messageData", addCount);
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        CorrelationData correlationData = new CorrelationData(productId.toString());
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, RabbitMQConstants.Product_ROUTING_KEY, map, correlationData);
    }

    public void sendRefreshCartCountRedis(Long userId) {
        logger.info("send 更新购物车数量，userId={}", userId);
        if (userId == null || userId == 0) {
            logger.error("sendRefreshCartCount error,userId exception");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("messageId", userId);
        map.put("messageData", userId);
        map.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        CorrelationData correlationData = new CorrelationData(userId.toString());
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, RabbitMQConstants.CART_ROUTING_KEY, map, correlationData);
    }
}
