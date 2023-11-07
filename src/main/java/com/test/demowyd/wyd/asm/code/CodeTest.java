package com.test.demowyd.wyd.asm.code;

/**
 * @program: spring-wyd
 * @description: 字节码内容学习
 * @author: Stone
 * @create: 2023-11-07 18:04
 **/
public class CodeTest {

    private String myName;

    private String test;

    public CodeTest(String test, String test2) {
        this.myName = test;
        this.test = test2;
    }

    public String myTest(String name) {
        if (!myName.equals(name)) {
            test = myName;
            myName = "111" + name;
        }
        return myName;
    }
}
