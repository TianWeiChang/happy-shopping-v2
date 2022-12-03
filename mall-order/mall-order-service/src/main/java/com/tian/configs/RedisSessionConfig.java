package com.tian.configs;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 当我们把这个服务部署多个节点时，用户请求，第一次被分发到节点A，第二次被分发到B节点，如果不适用分布式存储，只存在本地
 * 就会出现，用户第一次在A节点登录，登录信息在A节点上，第二次来到B节点上，没有登录信息，所以用户需要重新登陆
 * 自此，搞了个分布式session存储于Redis中
 * 更多大家可以参考：<link ref="https://www.cnblogs.com/david1216/p/11468772.html"/>
 */
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24)//session过期时间(秒)
@Configuration
public class RedisSessionConfig {
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        //让springSession不再执行config命令
        return ConfigureRedisAction.NO_OP;
    }
}