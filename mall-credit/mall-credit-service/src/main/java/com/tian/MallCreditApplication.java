package com.tian;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tianwc  公众号：java后端技术全栈、面试专栏
 * @version 1.0.0
 * @date 2022年12月02日 16:20
 */
@MapperScan("com.tian.mapper")
@DubboComponentScan("com.tian.service")
@SpringBootApplication
public class MallCreditApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCreditApplication.class, args);
    }
}
