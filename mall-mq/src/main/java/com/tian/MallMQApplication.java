package com.tian;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages={"com.tian"})
@SpringBootApplication
public class MallMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallMQApplication.class, args);
    }

}
