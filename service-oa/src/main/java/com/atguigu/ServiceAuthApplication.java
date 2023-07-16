package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * auth.ServiceAuthApplication
 *
 * @author Jia Yaoyi
 * @date 2023/07/16
 */
@SpringBootApplication
@MapperScan("com.atguigu.*.mapper")
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }
}
