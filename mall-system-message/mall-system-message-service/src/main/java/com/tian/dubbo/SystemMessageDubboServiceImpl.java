package com.tian.dubbo;

import com.alibaba.fastjson.JSON;
import com.tian.config.RedisUtil;
import com.tian.constants.RedisCacheKey;
import com.tian.dto.SystemMessageDto;
import com.tian.entity.SystemMessage;
import com.tian.service.SystemMessageDubboService;
import com.tian.service.SystemMessageService;
import com.tian.utils.Result;
import com.tian.utils.ResultGenerator;
import com.tian.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2023年01月10日 20:34
 * <p>
 * 站内信模板 dubbo服务类
 */
@DubboService(version = "1.0.0")
public class SystemMessageDubboServiceImpl implements SystemMessageDubboService {
    private Logger logger = LoggerFactory.getLogger(SystemMessageDubboServiceImpl.class);
    @Resource
    private SystemMessageService systemMessageService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result querySystemMessageById(Long id) {
        String cache = redisTemplate.opsForValue().get(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + id);
        if (StringUtils.isEmpty(cache)) {
            SystemMessage systemMessage = systemMessageService.selectByPrimaryKey(id);
            if (systemMessage == null) {
                redisTemplate.opsForValue().set(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + id, JSON.toJSONString(new SystemMessage()));
                return ResultGenerator.genFailResult("模板不存在");
            }
            redisTemplate.opsForValue().set(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + id, JSON.toJSONString(systemMessage));
            return ResultGenerator.genSuccessResult(systemMessage);
        }
        SystemMessage systemMessage = JSON.parseObject(cache, SystemMessage.class);
        //防止数据库中不存在同时缓存中也不存在的情况
        if (systemMessage != null && systemMessage.getId() == null) {
            return ResultGenerator.genFailResult("模板不存在");
        }
        return ResultGenerator.genSuccessResult(systemMessage);
    }

    @Override
    public Result add(SystemMessageDto systemMessageDto) {
        if (systemMessageDto == null) {
            return ResultGenerator.genFailResult("参数为空");
        }
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setContent(systemMessageDto.getContent());
        systemMessage.setCreateTime(systemMessageDto.getCreateTime());
        systemMessage.setDeleted(systemMessageDto.getDeleted());
        systemMessage.setLink(systemMessageDto.getLink());
        systemMessage.setSendType(systemMessageDto.getSendType());
        systemMessage.setTitle(systemMessageDto.getTitle());
        systemMessage.setUpdateTime(systemMessageDto.getUpdateTime());
        int flag = systemMessageService.insert(systemMessage);
        if (flag == 1) {
            //新增成功时，把站内信模板放入到Redis缓存中
            redisTemplate.opsForValue().set(RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + systemMessage.getId(), JSON.toJSONString(systemMessage));
            return ResultGenerator.genSuccessResult("添加成功");
        }
        return ResultGenerator.genFailResult("添加失败");
    }


    @Override
    public Result update(SystemMessageDto systemMessageDto) {
        if (systemMessageDto == null) {
            return ResultGenerator.genFailResult("参数为空");
        }
        String cacheKey = RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + systemMessageDto.getId();
        String cache = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isEmpty(cache)) {
            return ResultGenerator.genFailResult("模板不存在");
        }
        SystemMessage systemMessage = JSON.parseObject(cache, SystemMessage.class);
        if (systemMessage == null) {
            return ResultGenerator.genFailResult("模板不存在");
        }
        if (systemMessage.getId() == null) {
            return ResultGenerator.genFailResult("模板不存在");
        }

        boolean idDelete = redisTemplate.delete(cacheKey);
        if (!idDelete) {
            return ResultGenerator.genFailResult("缓存删除失败");
        }

        systemMessage.setContent(systemMessageDto.getContent());
        systemMessage.setCreateTime(systemMessageDto.getCreateTime());
        systemMessage.setDeleted(systemMessageDto.getDeleted());
        systemMessage.setLink(systemMessageDto.getLink());
        systemMessage.setSendType(systemMessageDto.getSendType());
        systemMessage.setTitle(systemMessageDto.getTitle());
        systemMessage.setUpdateTime(systemMessageDto.getUpdateTime());

        int flag = systemMessageService.updateByPrimaryKey(systemMessage);
        if (flag == 1) {
            //新增成功时，把站内信模板放入到Redis缓存中
            try {
                Thread.sleep(200L);
                //延迟双删
                redisTemplate.delete(cacheKey);
            } catch (InterruptedException e) {
                logger.warn("延迟双删失败,cacheKey={}", cacheKey);
            }
            redisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(systemMessage));
            return ResultGenerator.genSuccessResult("添加成功");
        }
        return ResultGenerator.genFailResult("添加失败");
    }

    @Override
    public Result delete(SystemMessageDto systemMessageDto) {
        String cacheKey = RedisCacheKey.SYSTEM_MESSAGE_TEMPLATE_PRE + systemMessageDto.getId();
        redisTemplate.delete(cacheKey);
        int flag = systemMessageService.delete(systemMessageDto.getId());
        if (flag == 1) {
            redisTemplate.delete(cacheKey);
            return ResultGenerator.genSuccessResult("删除成功");
        }
        return ResultGenerator.genFailResult("添加失败");
    }
}