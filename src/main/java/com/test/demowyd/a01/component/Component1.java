package com.test.demowyd.a01.component;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Component1 {

    @Resource
    private Component2 component2;

    public void test1(){

    }
}
