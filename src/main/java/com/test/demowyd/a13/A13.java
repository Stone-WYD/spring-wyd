package com.test.demowyd.a13;

public class A13 {

    interface Foo{
        void foo();
    }

    interface InvocationHandler{
        void invoke();
    }

    static class Target implements Foo{

        @Override
        public void foo() {
            System.out.println("target foo");
        }
    }

    public static void main(String[] args) {
        Foo proxy = new $Proxy0(() -> {
            //1.功能增强
            System.out.println("before...");
            //2.调用目标
            new Target().foo();
        });
        proxy.foo();
    }
}
