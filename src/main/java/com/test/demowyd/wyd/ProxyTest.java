package com.test.demowyd.wyd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: spring-wyd
 * @description: 代理类测试
 * @author: Stone
 * @create: 2023-10-30 09:40
 **/
public class ProxyTest {

    interface Foo {
        void foo();
    }

    static final class Target implements Foo {
        public void foo() {
            System.out.println("target foo");
        }
    }

    public static void main(String[] args) throws Exception {
        Target target = new Target();
        Method fooMethod = Foo.class.getMethod("foo");
        fooMethod.invoke(target);
    }
}
