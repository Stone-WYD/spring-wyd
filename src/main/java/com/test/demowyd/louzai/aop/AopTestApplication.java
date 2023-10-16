package com.test.demowyd.louzai.aop;

import com.test.demowyd.louzai.aop.component.Louzai;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AopTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AopTestApplication.class, args);

        Louzai louzai = applicationContext.getBean(Louzai.class);
        louzai.everyDay();
    }
}
