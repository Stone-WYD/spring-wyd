package com.test.demowyd.louzai;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: spring-wyd
 * @description: 测试类
 * @author: Stone
 * @create: 2023-10-09 15:30
 **/
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context =new
                ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        LouzaiBean louzaiBean = (LouzaiBean) context.getBean("louzaiBean");
        louzaiBean.work();
        ((ClassPathXmlApplicationContext) context).destroy();
    }
}
