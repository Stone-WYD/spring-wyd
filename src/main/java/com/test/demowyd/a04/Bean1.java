package com.test.demowyd.a04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class Bean1 {

    private static final Logger log =  LoggerFactory.getLogger(Bean1.class);

    private Bean2 bean2;

    @Autowired
    public void setBean2(Bean2 bean2){
        log.debug("@Autowired注解生效：{}",bean2 );
        this.bean2 = bean2;
    }

    private Bean3 bean3;

    @Resource
    public void setBean3(Bean3 bean3){
        log.debug("@Resource注解生效：{}",bean3 );
        this.bean3 = bean3;
    }

    private String javaHome;

    @Autowired
    public void setJavaHome(@Value("${JAVA_HOME}") String javaHome ){
        log.debug("@Value注解生效：{}",javaHome );
        this.javaHome = javaHome;
    }

    public String getJavaHome(){
        return this.javaHome;
    }

    @PostConstruct
    public void init(){
        log.debug("@PostConstruct 生效");
    }

    @PreDestroy
    public void destroy(){
        log.debug("@Predestroy 生效");
    }

    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", javaHome='" + javaHome + '\'' +
                '}';
    }
}
