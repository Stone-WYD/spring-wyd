package com.test.demowyd.a13;

import com.test.demowyd.a13.A13.*;

public class $Proxy0 implements Foo {

    private InvocationHandler h;

    public $Proxy0(InvocationHandler h){
        this.h = h;
    }

    @Override
    public void foo() {

        h.invoke();

    }
}
