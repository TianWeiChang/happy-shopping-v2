package com.tian.receiver;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.tian.config.RedisUtil;
import com.tian.constants.RedisCacheKey;
import com.tian.dao.LoginLogMapper;
import com.tian.dao.MallShoppingCartItemMapper;
import com.tian.entity.LoginLog;
import com.tian.mq.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 消息队列--消费者
 */
@Component
public class MQMsgReceiver {

    private Logger logger = LoggerFactory.getLogger(MQMsgReceiver.class);
    @Resource
    private LoginLogMapper loginLogMapper;
    @Resource
    private MallShoppingCartItemMapper mallShoppingCartItemMapper;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 登录日志记录
     */
    @RabbitListener(queues = RabbitMQConstants.LOGIN_LOG_QUEUE)
    @RabbitHandler
    public void processLoginLog(Message message, Channel channel, Map testMessage) {
        logger.info("receiver login log msg：{}", JSON.toJSONString(testMessage));
        LoginLog loginLog = new LoginLog();
        loginLog.setCreateTime(new Date());
        loginLog.setUserId(JSON.parseObject(JSON.toJSONString(testMessage)).getInteger("messageId"));
        try {
            int flag = loginLogMapper.insertOne(loginLog);
            if (flag > 0) {
                //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会再发
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            }
        } catch (Exception ex) {
            logger.error(" login log insert failed, error:", ex);
        }
    }

    /**
     * 订单删除，产品库存量调整
     */
    @RabbitListener(queues = RabbitMQConstants.PRODUCT_QUEUE)
    @RabbitHandler
    public void processOrder(Message message, Channel channel, Map testMessage) {
        logger.info("receiver product msg：{}", JSON.toJSONString(testMessage));
        try {
            // TODO: 2022/1/26 undo
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception ex) {
            logger.error(" product failed, error:", ex);
        }
    }

    /**
     * 购物车数据更细
     */
    @RabbitListener(queues = RabbitMQConstants.CART_QUEUE)
    @RabbitHandler
    public void processCart(Message message, Channel channel, Map testMessage) {
        Long userId = JSON.parseObject(JSON.toJSONString(testMessage)).getLong("messageId");
        logger.info("receiver 购物车数量更新,userId={}", userId);
        Integer cartCount = mallShoppingCartItemMapper.findCountByUserId(userId);
        logger.info("receiver 购物车数据更新：userId={},更新数量={}", userId, cartCount);
        redisUtil.set(RedisCacheKey.USER_CART_COUNT_KEY + userId, cartCount == null ? 0 : cartCount);
    }

}