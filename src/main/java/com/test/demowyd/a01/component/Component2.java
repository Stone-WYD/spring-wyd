package com.test.demowyd.a01.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Component2 {

    @Autowired
    private Component1 component1;

    public void test2(){

    }
}
