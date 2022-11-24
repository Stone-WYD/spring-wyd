package com.test.demowyd.a02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;


public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //bean定义
        AbstractBeanDefinition configuration = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",configuration);

        //Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(p-> System.out.println(p));

        //给beanfactory添加一些常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        //Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(p-> System.out.println(p));

        //执行工厂后处理器
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().stream().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(p-> System.out.println(p));


        //添加bean后处理器
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanPostProcessor -> beanFactory.addBeanPostProcessor(beanPostProcessor));

        //预先实例化所有的单例对象
        beanFactory.preInstantiateSingletons(); // 准备好所有单例
        System.out.println(">>>>>>>>>>>>>>>>>>>");

        System.out.println(beanFactory.getBean(Bean1.class));
        System.out.println(beanFactory.getBean(Bean2.class));
        System.out.println(beanFactory.getBean(Config.class));



    }


    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

    }

    interface Inter {

    }


    static class Bean1 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1() {
            log.debug("构造 Bean1()");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

    }

    static class Bean2 {
        private static final Logger log = LoggerFactory.getLogger(Bean2.class);

        public Bean2() {
            log.debug("构造 Bean2()");
        }

    }
}
