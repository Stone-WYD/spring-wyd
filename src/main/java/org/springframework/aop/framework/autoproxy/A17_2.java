package org.springframework.aop.framework.autoproxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class A17_2 {
    //高级切面转低级切面
    static class Aspect{

        @Before("execution(* foo())")
        public void before1(){
            System.out.println("before1...");
        }

        @Before("execution(* foo())")
        public void before2(){
            System.out.println("before2...");
        }

        @After("execution(* foo())")
        public void after() {
            System.out.println("after...");
        }

        public void afterReturning() {
            System.out.println("afterReturning");
        }

        public void afterThrowing() {
            System.out.println("afterThrowing");
        }

        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            try {
                System.out.println("around...before");
                return pjp.proceed();
            } finally {
                System.out.println("around...after");
            }
        }
    }

    static class Target{
        public void foo(){
            System.out.println("target foo");
        }
    }

    public static void main(String[] args) {

        AspectInstanceFactory factory = new SingletonAspectInstanceFactory(new Aspect());
        List<Advisor> list = new ArrayList<>();
        for (Method method : Aspect.class.getMethods()){
            if (method.isAnnotationPresent(Before.class)){
                //解析切点
                String expression = method.getAnnotation(Before.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);
                //通知类
                AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method,pointcut,factory);
                //切面
                Advisor advisor = new DefaultPointcutAdvisor(advice);
                list.add(advisor);
            }

            if (method.isAnnotationPresent(After.class)){
                String expression = method.getAnnotation(After.class).value();
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression(expression);

                AspectJAfterAdvice advice = new AspectJAfterAdvice(method,pointcut,factory);

                AspectJPointcutAdvisor advisor = new AspectJPointcutAdvisor(advice);
                list.add(advisor);
            }
        }
        for (Advisor advisor : list) {
            System.out.println(advisor);
        }
    }
}
