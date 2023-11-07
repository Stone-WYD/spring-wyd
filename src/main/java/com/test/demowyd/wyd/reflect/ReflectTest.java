package com.test.demowyd.wyd.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: spring-wyd
 * @description:
 * @author: Stone
 * @create: 2023-11-07 10:07
 **/
public class ReflectTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method testMethod = MyClass.class.getMethod("test", Integer.class, String.class, MyClass2.class);
        MyClass myClass = new MyClass();

        // 参数准备
        Integer i = 1;
        String s = ": yxy and ";
        MyClass2 myClass2 = new MyClass2();
        myClass2.setName("wyd");
        Object[] myArgs = {i, s, myClass2};

        String result = (String) testMethod.invoke(myClass, myArgs);
        System.out.println(result);
    }

    static class MyClass {
        public String test(Integer i, String s, MyClass2 myClass2) {
            return i + s + myClass2.getName();
        }
    }

    static class MyClass2 {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
