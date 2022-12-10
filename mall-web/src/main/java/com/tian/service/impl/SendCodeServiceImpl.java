package com.tian.service.impl;

import com.tian.mq.MQMsgSender;
import com.tian.service.SendCodeService;
import com.tian.utils.CreateRandomCode;
import com.tian.utils.RedisPrefixConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月08日 20:50
 * <p>
 * 发送验证码
 */
@Service
public class SendCodeServiceImpl implements SendCodeService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private MQMsgSender mqMsgSender;

    @Override
    public boolean sendCode(String phone) {
        //生成随机6位数
        String code = CreateRandomCode.generateCode();
        //缓存到Redis中 验证码 120秒  两分钟有效
        redisTemplate.opsForValue().set(RedisPrefixConstant.SEND_CODE_KEY_PREFIX + phone, code, 120, TimeUnit.SECONDS);

        //获取短信验证码模板
        String msgTemplate = "欢迎来到【XXX】，你的注册验证码：{}";

        String msg = msgTemplate.replace("{}", code);
        //调用短信供应商，发送短信
        /**
         * 这里没什么技术含量，对接短信很麻烦，这里就不对接了。
         *
         * 就是发起一个http请求，然后把我们的手机号、短信、一些短信商给的必须参数即可。
         *
         * 对着他们API来， 返回发送成功了，我们就可以返回给用户提示：验证码发送成功！
         */
        mqMsgSender.sendCode(phone, code);
        return true;
    }
}
