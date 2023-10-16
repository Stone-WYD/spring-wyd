package com.test.demowyd.wyd.construct.component;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: spring-wyd
 * @description: 模拟 cpu 飙升场景
 * @author: Stone
 * @create: 2023-10-12 11:49
 **/
@Component
public class CpuReaper {

    @PostConstruct
    public void cpuReaper(){
        int num = 0;
        long start = System.currentTimeMillis() / 1000;
        while (true) {
            num++;
            if (num == Integer.MAX_VALUE) {
                System.out.println("reset");
                num = 0;
            }
            if ((System.currentTimeMillis() / 1000) - start > 1000) {
                return;
            }
        }
    }
}
