package com.test.demowyd.a13;

import com.test.demowyd.a13.A13.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class $Proxy0 extends Proxy implements Foo {


    public $Proxy0(InvocationHandler h){
        super(h);
    }

    static Method foo;
    static Method bar;

    static {
        try {
            foo = Foo.class.getMethod("foo");
            bar = Foo.class.getMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void foo() {
        try {
            h.invoke(this, foo,new Object[0]);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int bar() {
        try {
            return (int) h.invoke(this, bar,new Object[0]);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


}
