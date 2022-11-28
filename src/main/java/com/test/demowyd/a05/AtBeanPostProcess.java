package com.test.demowyd.a05;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.util.Set;

public class AtBeanPostProcess implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
            //不走类加载，没有反射，效率会比较高
            MetadataReader reader = factory.getMetadataReader("com/test/demowyd/a05/Config");
            Set<MethodMetadata> methods = reader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
            for (MethodMetadata method : methods) {
                System.out.println("------");
                System.out.println(method);
                String iniMethod = method.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();

                //工厂方法模式
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
                beanDefinitionBuilder.setFactoryMethodOnBean(method.getMethodName(),"config");
                //属性注入
                beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
                if (iniMethod.length()>0){
                    beanDefinitionBuilder.setInitMethodName(iniMethod);
                }
                AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
                if (configurableListableBeanFactory instanceof DefaultListableBeanFactory){
                    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
                    defaultListableBeanFactory.registerBeanDefinition(method.getMethodName(),beanDefinition);
                }

            }
        }catch (Exception e){

        }
    }
}
