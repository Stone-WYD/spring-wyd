package com.test.demowyd.a48;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Configuration
public class A48_3 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_3.class);
        MyService myService = context.getBean(MyService.class);
        myService.doService();
        context.close();
    }

    @Bean
    public SmartInitializingSingleton smartInitializingSingleton(ConfigurableApplicationContext context){
        return () -> {
            for (String name : context.getBeanDefinitionNames()) {
                Object bean = context.getBean(name);
                for (Method method : bean.getClass().getMethods()){
                    if (method.isAnnotationPresent(MyListener.class)) {
                        context.addApplicationListener(event -> {
                            try {
                                Class<?> eventType = method.getParameterTypes()[0];
                                if (eventType.isAssignableFrom(event.getClass())) {
                                    //确认传参类型eventType是传参event类型的父类
                                    method.invoke(bean,event);
                                }
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
            }
        };
    }

    @Component
    static class MyService{
        private static final Logger logger = LoggerFactory.getLogger(MyService.class);

        @Autowired
        private ApplicationEventPublisher publisher;

        public void doService(){
            logger.debug("执行业务内容。。。");
            publisher.publishEvent(new MyEvent("doService"));
        }
    }

    @Component
    static class SmsService{
        private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

        @MyListener
        public void listener(MyEvent myEvent){
            logger.debug("发送短信。。。");
        }
    }

    @Component
    static class EmailService{
        private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

        @MyListener
        public void listener(MyEvent myEvent){
            logger.debug("发送邮件。。。");
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface MyListener{

    }

    static class MyEvent extends ApplicationEvent{
        public MyEvent(Object source) {
            super(source);
        }
    }

}
