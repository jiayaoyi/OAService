package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * auth.ServiceAuthApplication
 *
 * @author Jia Yaoyi
 * @date 2023/07/16
 */
@SpringBootApplication
@MapperScan("com.atguigu.*.mapper")
@EnableCaching
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }
}
