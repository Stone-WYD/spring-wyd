package com.test.demowyd.louzai.aop.order;

import com.test.demowyd.louzai.aop.component.Louzai;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: spring-wyd
 * @description: bean 后处理器，要在aop的后处理器之后执行
 * @author: Stone
 * @create: 2023-11-13 17:45
 **/
@Order(value = Integer.MIN_VALUE)
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("louzai")){
            System.out.println("没想到吧，aop没能生效，因为我在这处理了一下");
            return new Louzai();
        } else
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
