package com.tian.configs;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月09日 11:03
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author qinxubang
 * @Date 2021/4/23 14:41
 */
@Configuration
public class RabbitmqConfig {

    private Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    // Spring-Boot的依赖注入功能
    // 注入连接工厂：Spring-Boot的依赖注入：启动时，RabbitProperties会自动读取RabbitMQ的配置信息。
    // 若配置参数命名符合其规范，会注入到spring的bean容器中，创建生成连接工厂
    @Resource
    private CachingConnectionFactory connectionFactory;

    // 注入连接工厂：编写代码，读取application.properties配置文件中的MQ的配置参数，生成连接工厂
    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory(
            @Value("${spring.rabbitmq.host}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username}") String username,
            @Value("${spring.rabbitmq.password}") String password,
            @Value("${spring.rabbitmq.virtual-host}") String virtualHost) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);

        return cachingConnectionFactory;
    }

    /**
     * 构建RabbitMQ发送消息的操作组件实例
     * 生产者的发送确认机制
     */
    @Bean(name = "rabbitMQTemplate")
    public RabbitTemplate rabbitTemplate() {
        // 生产者确认消息是否发送过去了
        connectionFactory.setPublisherConfirms(true);

        // 生产者发送消息后，返回反馈消息
        connectionFactory.setPublisherReturns(true);

        // 构建rabbitTemlate操作模板
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);

        // 生产者发送消息后，如果发送成功，则打印“消息发送成功”的日志信息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });

        // 生产者发送消息后，若发送失败，则输出“消息发送失败”的日志信息
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
            }
        });

        return rabbitTemplate;
    }

    /**
     * 设置单个消费者
     * 消费者的Ack确认机制为AUTO
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());

        // 设置消费者的个数
        containerFactory.setConcurrentConsumers(1);
        // 设置消费者的最大值
        containerFactory.setMaxConcurrentConsumers(1);
        // 设置消费者每次拉取的消息数量，即消费者一次拉取几条消息
        containerFactory.setPrefetchCount(1);

        // 设置确认消息模型为自动确认消费AUTO，目的是防止消息丢失和消息被重复消费
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return containerFactory;
    }
}