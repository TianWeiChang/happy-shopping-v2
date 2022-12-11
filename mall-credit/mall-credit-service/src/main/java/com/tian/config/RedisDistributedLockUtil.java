package com.tian.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月10日 19:21
 * <p>
 * 分布式锁
 */
@Component
public class RedisDistributedLockUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLockUtil.class);

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 加锁方法仅针对单实例 Redis,哨兵、集群模式无法使用
     *
     * @param lockKey  加锁键
     * @param clientId 加锁客户端唯一标识(采用UUID)
     * @param expire  锁过期时间 单位毫秒
     * @return true标识加锁成功、false代表加锁失败
     */
    public Boolean tryLock(String lockKey, String clientId, long expire) {
        try {
            return redisTemplate
                    .execute((RedisCallback<Boolean>) redisConnection -> {
                        Jedis jedis = (Jedis) redisConnection.getNativeConnection();
                        SetParams params = new SetParams();
                        params.nx();
                        params.px(expire);
                        String result = jedis.set(lockKey, clientId, params);
                        if (LOCK_SUCCESS.equals(result)) {
                            return Boolean.TRUE;
                        }
                        return Boolean.FALSE;
                    });
        } catch (Exception e) {
            logger.error("tryLock error", e);
        }

        return false;
    }

    /**
     * 释放锁，保持原子性操作，采用了lua脚本
     *
     * @param lockKey  加锁键
     * @param clientId 加锁客户端唯一标识(采用UUID)
     * @return true标识解锁成功、false代表解锁失败
     */
    public Boolean unLock(String lockKey, String clientId) {
        try {
            return redisTemplate
                    .execute((RedisCallback<Boolean>) redisConnection -> {
                        Jedis jedis = (Jedis) redisConnection.getNativeConnection();
                        Object result = jedis.eval(RELEASE_LOCK_SCRIPT,
                                Collections.singletonList(lockKey),
                                Collections.singletonList(clientId));
                        if (RELEASE_SUCCESS.equals(result)) {
                            return Boolean.TRUE;
                        }
                        return Boolean.FALSE;
                    });
        } catch (Exception e) {
            logger.error("unlock error", e);
        }
        return Boolean.FALSE;
    }
}