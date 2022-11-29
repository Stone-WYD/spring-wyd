package com.test.demowyd.a13;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class A13 {

    interface Foo{
        void foo();

        int bar();
    }

/*    interface InvocationHandler{
        Object invoke(Object proxy, Method m , Object[] args) throws InvocationTargetException, IllegalAccessException;
    }*/

    static class Target implements Foo{

        @Override
        public void foo() {
            System.out.println("target foo");
        }

        @Override
        public int bar() {
            System.out.println("target bar");
            return 1;
        }
    }

    public static void main(String[] param) {

        Foo proxy = new $Proxy0((Object proxy0, Method m,Object[] args) -> {
            //1.功能增强
            System.out.println("before...");
            //2.调用目标
//            new Target().foo();
            return m.invoke(new Target(),args);
        });
        proxy.foo();
        System.out.println(proxy.bar());
    }
}
