package org.springframework.aop.framework.autoproxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

public class A17 {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1",Aspect1.class);
        context.registerBean("config",Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);

        //BeanPostProcessor
        //创建 -> (*) 依赖注入 -> 初始化 (*)

        context.refresh();

/*        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }*/

        /*
            第一个重要方法 findEligibleAdvisors 找到有【资格】的 Advisors
                a. 有【资格】的 Advisor 一部分是低级的, 可以由自己编写, 如下例中的 advisor3
                b. 有【资格】的 Advisor 另一部分是高级的, 由本章的主角解析 @Aspect 后获得
         */
        AnnotationAwareAspectJAutoProxyCreator creator
                = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
        List<Advisor> advisors = creator.findEligibleAdvisors(Target1.class, "target1");
        for (Advisor advisor : advisors) {
            System.out.println(advisor);
        }

        /*
            第二个重要方法 wrapIfNecessary
                a. 它内部调用 findEligibleAdvisors, 只要返回集合不空, 则表示需要创建代理
         */
        Object target1 = creator.wrapIfNecessary(new Target1(), "target1", "target1");
        System.out.println(target1.getClass());
        Object target2 = creator.wrapIfNecessary(new Target2(), "target2", "target2");
        System.out.println(target2.getClass());

        ((Target1) target1).foo();

    }

    static class Target1{
        public void foo(){
            System.out.println("target1 foo");
        }
    }

    static class Target2{
        public void bar(){
            System.out.println("target2 bar");
        }
    }

    @Aspect //高级切面
    static class Aspect1{
        @Before("execution(* foo())")
        public void before(){
            System.out.println("aspect1 before...");
        }

        @After("execution(* foo())")
        public void after(){
            System.out.println("aspect1 after...");
        }
    }

    @Configuration
    static class Config{

        @Bean
        public Advisor advisor3(){
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();//切点
            pointcut.setExpression("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut,advice3()); //返回一个切面（其中包含切点+通知）
        }

        @Bean
        public MethodInterceptor advice3(){
            return invocation -> {
                System.out.println("advice3 before...");
                Object o = invocation.proceed();
                System.out.println("advice3 after...");
                return o;
            };
        }


    }
}
