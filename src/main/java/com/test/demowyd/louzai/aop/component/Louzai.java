package com.test.demowyd.louzai.aop.component;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Data
@Service
public class Louzai {

    @Resource
    private Component1 component1;

    public void everyDay(){
        System.out.println("睡觉。。。");
    }
}
