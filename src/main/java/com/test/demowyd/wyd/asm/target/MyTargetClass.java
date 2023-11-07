package com.test.demowyd.wyd.asm.target;

import com.test.demowyd.wyd.web.Controller1;

/**
 * @program: spring-wyd
 * @description: 使用 asm 计划生成的类
 * @author: Stone
 * @create: 2023-11-07 15:42
 **/
public class MyTargetClass {

    private Controller1 controller1;

    public MyTargetClass(Controller1 controller1){
        this.controller1 = controller1;
    }

    public Object invoke(String index, Object target, Object[] args){
        if ("/myOwnTest".equals(index)) {
            return ((Controller1) target).test5((String) args[0],(Controller1.User) args[1]);
        } else if ("/test2".equals(index)) {
            return ((Controller1) target).test2(((String) args[0]));
        }
        return null;
    }
}
