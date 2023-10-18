package com.test.demowyd.wyd.spring_netty;

import com.test.demowyd.wyd.construct.DemomydApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @program: spring-wyd
 * @description: spring_netty_test spring启动类
 * @author: Stone
 * @create: 2023-10-18 13:58
 **/
@SpringBootApplication
public class MySpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class, args);
    }
}
