package com.test.demowyd.louzai;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @program: spring-wyd
 * @description: 自定义bean后处理器
 * @author: Stone
 * @create: 2023-10-09 15:28
 **/
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("5.BeanPostProcessor.postProcessBeforeInitialization⽅法：到学校报名啦");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("8.BeanPostProcessor#postProcessAfterInitialization⽅法：终于毕业，拿到毕业证啦！");
        return bean;
    }
}
