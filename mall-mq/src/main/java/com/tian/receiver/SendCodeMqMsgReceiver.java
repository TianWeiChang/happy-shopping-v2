package com.tian.receiver;

import com.rabbitmq.client.Channel;
import com.tian.mq.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月09日 10:51
 * <p>
 * 发送验证码
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = RabbitMQConstants.FANOUT_EXCHANGE, type = ExchangeTypes.DIRECT),
        value = @Queue(value = RabbitMQConstants.SEND_CODE, autoDelete = "true"),
        key = (RabbitMQConstants.SEND_CODE_ROUTING_KEY)
))
public class SendCodeMqMsgReceiver {
    private Logger logger = LoggerFactory.getLogger(SendCodeMqMsgReceiver.class);

    @RabbitHandler
    public void sendCode(Message message, Channel channel, Map map) throws IOException {
        logger.info("发送验证码：message={},msg={}", message, map);
        try {
            // TODO: 2022/1/26 undo
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception ex) {
            channel.basicCancel("异常了");
            logger.error(" product failed, error:", ex);
        }
    }
}
