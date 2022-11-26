package com.test.demowyd.a05;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

public class ComponentScanPostProcessor implements BeanFactoryPostProcessor {
    @Override //context.refresh
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            ComponentScan componentScan = AnnotationUtils.findAnnotation(Config.class, ComponentScan.class);
            if (componentScan != null) {
                for (String p : componentScan.basePackages()) {
                    System.out.println(p);
                    //把路径中“.”变成“/”
                    String path = "classpath*:" + p.replace(".", "/") + "/**/*.class";
                    System.out.println(path);
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);

                    CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
                    AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
                    for (Resource resource : resources) {
//                    System.out.println(resource);
                        MetadataReader reader = factory.getMetadataReader(resource);
                        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
//                    System.out.println("类名为：" +  reader.getClassMetadata().getClassName());
//                    System.out.println("是否加了Component注解：" + annotationMetadata.hasAnnotation(Component.class.getName()));
//                    System.out.println("是否加了Component的派生注解：" + annotationMetadata.hasMetaAnnotation(Component.class.getName()));

                        if (annotationMetadata.hasMetaAnnotation(Component.class.getName())
                                || annotationMetadata.hasAnnotation(Component.class.getName())) {
                            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.
                                    genericBeanDefinition(reader.getClassMetadata().getClassName()).getBeanDefinition();

                            if (configurableListableBeanFactory instanceof DefaultListableBeanFactory) {
                                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
                                String name = generator.generateBeanName(beanDefinition, defaultListableBeanFactory);
                                defaultListableBeanFactory.registerBeanDefinition(name, beanDefinition);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
