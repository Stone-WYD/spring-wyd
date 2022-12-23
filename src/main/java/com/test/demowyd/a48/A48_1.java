package com.test.demowyd.a48;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class A48_1 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_1.class);
        MyService myService = context.getBean(MyService.class);
        myService.doBusiness();
        context.close();
    }

    //自定义事件
    static class MyEvent extends ApplicationEvent{
        public MyEvent(Object source) {
            super(source);
        }
    }

    @Component
    static class MyService{
        private static final Logger logger = LoggerFactory.getLogger(MyService.class);

        @Autowired
        private ApplicationEventPublisher publisher;
        public void doBusiness(){
            logger.debug("doBusiness...");
            publisher.publishEvent(new MyEvent("doBusiness..."));
        }
    }

    @Component
    static class SmsApplicationListener implements ApplicationListener<MyEvent>{

        private static final Logger logger =LoggerFactory.getLogger(SmsApplicationListener.class);

        @Override
        public void onApplicationEvent(MyEvent event) {
            logger.debug("发送短信...");
        }
    }

    @Component
    static class EmailApplicationListener implements ApplicationListener<MyEvent>{

        private static final Logger logger = LoggerFactory.getLogger(EmailApplicationListener.class);

        @Override
        public void onApplicationEvent(MyEvent event) {
            logger.debug("发送邮件...");
        }
    }

}
