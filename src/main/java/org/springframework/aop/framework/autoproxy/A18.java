package org.springframework.aop.framework.autoproxy;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//模拟调用链过程
public class A18 {

    static class Target {
        public void foo(){
            System.out.println("Target.foo()");
        }
    }

    static class Advice1 implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("advice1 before...");
            Object result = invocation.proceed();
            System.out.println("advice2 after...");
            return result;
        }
    }

    static class Advice2 implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("advice2 before...");
            Object result = invocation.proceed();
            System.out.println("advice2 after...");
            return result;
        }
    }

    static class MyMethodInvocation implements MethodInvocation{

        private Method method;
        private Object[] args;
        private Object target;
        private List<MethodInterceptor> methodInterceptorList;

        private int count = 1;

        private MyMethodInvocation(Object target,Method method,Object[] args,List<MethodInterceptor> methodInterceptorList){
            this.method = method;
            this.target = target;
            this.args = args;
            this.methodInterceptorList = methodInterceptorList;
        }

        @Override
        public Method getMethod() {
            return method;
        }

        @Override
        public Object[] getArguments() {
            return args;
        }

        @Override
        public Object getThis() {
            return this;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return null;
        }

        @Override
        public Object proceed() throws Throwable {
            if (count > methodInterceptorList.size() ){
                return method.invoke(target,args);
            }
            MethodInterceptor methodInterceptor = methodInterceptorList.get(count++ - 1);
            return methodInterceptor.invoke(this);
        }
    }

    public static void main(String[] args) throws Throwable {
        Target target = new Target();
        List<MethodInterceptor> methodInterceptorList = List.of(
                new Advice1(),new Advice2()
        );
        MyMethodInvocation invocation = new MyMethodInvocation(target, Target.class.getMethod("foo"), new Object[0],methodInterceptorList);
        invocation.proceed();
    }


}
