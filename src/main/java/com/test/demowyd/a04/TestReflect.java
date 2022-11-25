package com.test.demowyd.a04;

import java.lang.reflect.Field;

public class TestReflect {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Bean1 bean1 = new Bean1();
        Field javaHome = Bean1.class.getDeclaredField("javaHome");
        javaHome.setAccessible(true);
        javaHome.set(bean1,"123");
        System.out.println(bean1.getJavaHome());
    }
}
