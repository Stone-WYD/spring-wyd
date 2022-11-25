package com.test.demowyd.a05;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class A05Application {
    private static final Logger log = LoggerFactory.getLogger(A05Application.class);

    public static void main(String[] args) {

        //新建一个干净的容器
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config",Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class); // @ComponentScan @Bean @Import @ImportResource
        context.registerBean(MapperScannerConfigurer.class,beanDefinition -> {
            beanDefinition.getPropertyValues().add("basePackage","com.test.demowyd.a05.mapper");
        });//mapper

        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()){
            System.out.println(beanDefinitionName);
        }

        context.close();

    }
}
