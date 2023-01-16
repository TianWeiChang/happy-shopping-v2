package com.tian.config;

import com.tian.mq.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 集成RabbitMQ
 * 公众号：Java后端技术全栈
 *
 * @author 田哥
 */
@SpringBootConfiguration
public class RabbitConfig {

    //队列 起名：TestDirectQueue
    @Bean
    public Queue loginLogDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(RabbitMQConstants.USER_MESSAGE_QUEUE, true);
    }


    @Bean
    public Queue orderDirectQueue() {
        return new Queue(RabbitMQConstants.ORDER_QUEUE, true);
    }

    @Bean
    public Queue productDirectQueue() {
        return new Queue(RabbitMQConstants.PRODUCT_QUEUE, true);
    }

    @Bean
    public Queue sendCodeQueue() {
        return new Queue(RabbitMQConstants.SEND_CODE, true);
    }

    @Bean
    public Queue initUserCreditDirectQueue() {
        return new Queue(RabbitMQConstants.INIT_USER_CREDIT_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitMQConstants.FANOUT_EXCHANGE);
    }

    @Bean
    public DirectExchange directExchangeInitUserCredit() {
        return new DirectExchange(RabbitMQConstants.INIT_USER_CREDIT__EXCHANGE);
    }

    //3个binding将交换机和相应队列连起来
    @Bean
    public Binding bindingLoginLoge() {
        return BindingBuilder.bind(loginLogDirectQueue()).to(directExchange()).with(RabbitMQConstants.LOGIN_LOG_ROUTING_KEY);
    }


    @Bean
    public Binding bindingOrder() {
        return BindingBuilder.bind(orderDirectQueue()).to(directExchange()).with(RabbitMQConstants.ORDER_ROUTING_KEY);
    }

    @Bean
    public Binding bindingProduct() {
        return BindingBuilder.bind(orderDirectQueue()).to(directExchange()).with(RabbitMQConstants.Product_ROUTING_KEY);
    }

    @Bean
    public Binding bindingSendCode() {
        return BindingBuilder.bind(sendCodeQueue()).to(directExchange()).with(RabbitMQConstants.SEND_CODE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingInitUserCredit() {
        return BindingBuilder.bind(initUserCreditDirectQueue()).to(directExchangeInitUserCredit()).with(RabbitMQConstants.INIT_USER_CREDIT_ROUTING_KEY);
    }
}
