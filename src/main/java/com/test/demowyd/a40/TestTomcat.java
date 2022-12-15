package com.test.demowyd.a40;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestTomcat {
        /*
        Server
        └───Service
            ├───Connector (协议, 端口)
            └───Engine
                └───Host(虚拟主机 localhost)
                    ├───Context1 (应用1, 可以设置虚拟路径, / 即 url 起始路径; 项目磁盘路径, 即 docBase )
                    │   │   index.html
                    │   └───WEB-INF
                    │       │   web.xml (servlet, filter, listener) 3.0
                    │       ├───classes (servlet, controller, service ...)
                    │       ├───jsp
                    │       └───lib (第三方 jar 包)
                    └───Context2 (应用2)
                        │   index.html
                        └───WEB-INF
                                web.xml
     */
    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException, LifecycleException {
        //1.创建tomcat对象
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");

        //2.创建文件夹，即 docBase 文件夹
        File docBase = Files.createTempDirectory("boot.").toFile();
        docBase.deleteOnExit();

        //3.创建Tomcat项目，在Tomcat中称为Context
        Context context = tomcat.addContext("",docBase.getAbsolutePath());

        WebApplicationContext springContext = getApplicationContext();

        //4.编程添加Servlet
        context.addServletContainerInitializer(new ServletContainerInitializer() {
            @Override
            public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
                HelloServlet helloServlet = new HelloServlet();
                servletContext.addServlet("helloServlet",helloServlet).addMapping("/hello");

//                DispatcherServlet dispatcherServlet = springContext.getBean(DispatcherServlet.class);
//                servletContext.addServlet("dispatcherServlet",dispatcherServlet).addMapping("/");
                for (ServletRegistrationBean registrationBean : springContext.getBeansOfType(ServletRegistrationBean.class).values()) {
                    registrationBean.onStartup(servletContext);
                }

            }
        }, Collections.emptySet());


        //5.启动tomcat
        tomcat.start();

        //6.创建连接器，设置监听接口
        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);
        tomcat.setConnector(connector);

    }

    public static WebApplicationContext getApplicationContext(){
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(Config.class);
        context.refresh();
        return context;
    }

    @Configuration
    static class Config{

        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet,"/");
        }

        @Bean
        public DispatcherServlet dispatcherServlet(WebApplicationContext applicationContext){
            return new DispatcherServlet(applicationContext);
        }

        @Bean
        public RequestMappingHandlerAdapter handlerAdapter(){
            RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
            handlerAdapter.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
            return handlerAdapter;
        }

        @RestController
        static class MyController{
            @GetMapping("/hello2")
            public Map<String,Object> hello(){
                return Map.of("hello2","hello2,spring!");
            }
        }

    }

}
