package com.test.demowyd.a01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootApplication
public class DemomydApplication {

    private static final Logger log = LoggerFactory.getLogger(DemomydApplication.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {


        ConfigurableApplicationContext context =
                SpringApplication.run(DemomydApplication.class, args);

        //context.getBean("aaa");

        System.out.println(context);

        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String,Object> map =(Map<String, Object>)singletonObjects.get(beanFactory);
        map.entrySet().forEach(p->{
            if (p.getKey().contains("component")) System.out.println(p.getValue());
        });

    }

}
