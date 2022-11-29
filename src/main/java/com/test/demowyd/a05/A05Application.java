package com.test.demowyd.a05;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

public class A05Application {
    private static final Logger log = LoggerFactory.getLogger(A05Application.class);

    public static void main(String[] args) throws IOException {

        //新建一个干净的容器
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config",Config.class);
//        context.registerBean(ConfigurationClassPostProcessor.class); // @ComponentScan @Bean @Import @ImportResource
//        context.registerBean(MapperScannerConfigurer.class,beanDefinition -> {
//            beanDefinition.getPropertyValues().add("basePackage","com.test.demowyd.a05.mapper");
//        });//mapper
        //模拟@ComponentScan
        context.registerBean(ComponentScanPostProcessor.class);

        //模拟@Bean
        context.registerBean(AtBeanPostProcess.class);

        //模拟@mapper
        context.registerBean(MapperPostProcessor.class);



        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()){
            System.out.println(beanDefinitionName);
        }

        context.close();

    }
}
