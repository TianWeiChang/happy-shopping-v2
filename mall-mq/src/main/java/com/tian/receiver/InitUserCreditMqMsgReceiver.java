package com.tian.receiver;

import com.tian.dto.UserCreditDto;
import com.tian.mq.RabbitMQConstants;
import com.tian.service.UserCreditDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月11日 15:34
 * <p>
 * 初始化用户积分 消息处理
 */
@Component
@RabbitListener(queues = RabbitMQConstants.INIT_USER_CREDIT_QUEUE)
public class InitUserCreditMqMsgReceiver {

    private Logger logger = LoggerFactory.getLogger(MQMsgReceiver.class);

    @DubboReference(version = "1.0.0", check = false)
    private UserCreditDubboService userCreditDubboService;

    @RabbitHandler
    public void process(Long userId) {
        try {
            logger.info("初始化积分账户 userId={}", userId);
            UserCreditDto userCreditDto = new UserCreditDto();
            userCreditDto.setUserId(userId);
            userCreditDubboService.initUserCredit(userCreditDto);
        } catch (Exception ex) {
            logger.error("初始化积分账户：消息消费失败，", ex);
        }
    }
}
