package com.test.demowyd.louzai.aop.order;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @program: spring-wyd
 * @description: 测试工厂后处理器的生成时机
 * @author: Stone
 * @create: 2023-11-13 17:31
 **/
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        System.out.println(" 此时创建了自定义的工厂后处理器 ~");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        AnnotationAwareAspectJAutoProxyCreator aopBean = beanFactory.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        aopBean.setOrder(Integer.MIN_VALUE + 1);
    }
}
