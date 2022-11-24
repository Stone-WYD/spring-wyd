package com.test.demowyd.a04;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

public class A04 {

    public static void main(String[] args) {
        //一个干净的context
        GenericApplicationContext context = new GenericApplicationContext();

        //注入bean
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
        context.registerBean("bean4", Bean4.class);

        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);//@Autowired @Value
        context.registerBean(CommonAnnotationBeanPostProcessor.class);//@Resource @PreDestroy @PostConstruct

        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory()); //@ConfigurationProperties

        //初始化容器
        context.refresh();

        System.out.println(context.getBean("bean4"));

        //关闭容器
        context.close();
    }
}
