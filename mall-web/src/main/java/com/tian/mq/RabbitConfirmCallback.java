package com.tian.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

//可以实现消息是否被确认，以及消息是否发送到MQ中（需要自行实现return部分代码）
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback{

    private Logger logger = LoggerFactory.getLogger(RabbitConfirmCallback.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("confirm: " + correlationData.getId() + ",ack=" + ack + ",cause:" + cause);
        if (ack) {
            logger.info("消息已确认 cause:{} ", correlationData);
        } else {
            logger.info("消息未确认 cause:{}", correlationData);
        }
    }
}
