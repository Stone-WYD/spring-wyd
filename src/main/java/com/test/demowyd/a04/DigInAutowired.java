package com.test.demowyd.a04;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DigInAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerSingleton("bean2",new Bean2());
        factory.registerSingleton("bean3",new Bean3()); //当作成品bean，不会再经过构建注入初始化等过程
        factory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver()); //@Value
        factory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);//${}的解析器

        //1.查找那些方法、属性加了@Autowired，这称之为InjectionMetadata
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(factory);

        Bean1 bean1 = new Bean1();
        System.out.println(bean1);
        //processor.postProcessProperties(null,bean1,null); //执行依赖注入

        //factory.registerSingleton("bean1",new Bean1());
        //processor.postProcessProperties(null,null,"bean1"); //执行依赖注入->错误，不能不给第二个参数赋值
        //System.out.println(factory.getBean("bean1"));

        Method method = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata",String.class,Class.class, PropertyValues.class);
        method.setAccessible(true);
        InjectionMetadata injectionMetadata = (InjectionMetadata) method.invoke(processor,"bean1", bean1.getClass(), null);//获取bean1上加了@Value,@Autowired的成员变量的信息

        //2.调用inject进行调用
        injectionMetadata.inject(bean1,null,null);
        System.out.println(bean1);

        //3.如何按类型查找类型
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        DependencyDescriptor dd1 = new DependencyDescriptor(bean3,false);
        Object o = factory.doResolveDependency(dd1, null, null, null);
        System.out.println(o);

        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2,0),false);
        Object o1 = factory.doResolveDependency(dd2, null, null, null);
        System.out.println(o1);

        Method setHome = Bean1.class.getDeclaredMethod("setJavaHome", String.class);
        DependencyDescriptor dd3 = new DependencyDescriptor(new MethodParameter(setHome,0),false);
        Object o2 = factory.doResolveDependency(dd3, null, null, null);
        System.out.println(o2);

    }
}
