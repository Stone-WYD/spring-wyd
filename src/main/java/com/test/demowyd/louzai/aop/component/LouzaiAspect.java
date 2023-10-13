package com.test.demowyd.louzai.aop.component;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LouzaiAspect {

    @Pointcut("execution(* com.test.demowyd.louzai.aop.component.Louzai.everyDay())")
    private void pointcut() {
    }

    // 前置通知
    @Before("pointcut()")
    public void myBefore() {
        System.out.println("吃饭。。。");
    }

    @After("pointcut()")
    public void myAfter() {
        System.out.println("打豆豆。。。");
    }
}
