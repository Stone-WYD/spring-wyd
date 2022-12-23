package com.test.demowyd.a48;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Configuration
public class A48_2 {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(A48_2.class);
        MyService myService = context.getBean(MyService.class);
        myService.doBusiness();
        context.close();
    }

    static class MyEvent extends ApplicationEvent {
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
            logger.debug("业务逻辑。。。");
            publisher.publishEvent(new MyEvent("MyService.doBusiness..."));
        }
    }

    @Component
    static class SmsService {
        private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

        @EventListener
        public void listen(MyEvent myEvent){
            logger.debug("短信服务。。。");
        }
    }

    @Component
    static class EmailService implements ApplicationListener<MyEvent> {
        private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

        @Override
        public void onApplicationEvent(MyEvent event) {
            logger.debug("邮件服务。。。");
        }
    }


    @Bean
    public ThreadPoolTaskExecutor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        return executor;
    }

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(ThreadPoolTaskExecutor executor){
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(executor);
        return simpleApplicationEventMulticaster;
    }


}
