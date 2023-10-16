package com.test.demowyd.wyd.construct.component;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class Component1 {

    private Component2 component2;

    public Component1(Component2 component2){
        this.component2 = component2;
    }

    public void test1(){
    }
}
