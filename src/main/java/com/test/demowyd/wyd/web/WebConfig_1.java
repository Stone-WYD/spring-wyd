package com.test.demowyd.wyd.web;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

@Configuration
@ComponentScan //不标注包名，会扫描当前类目录及子目录
@PropertySource("classpath:application.properties") //获取配置源文件
@EnableConfigurationProperties({WebMvcProperties.class, ServerProperties.class})
public class WebConfig_1 {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(ServerProperties serverProperties){
        return new TomcatServletWebServerFactory(serverProperties.getPort());
    }
    //创建DispatcherServlet
    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }
    //注册dispatherServlet,Spring MVC入口
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet,WebMvcProperties webMvcProperties){
        DispatcherServletRegistrationBean registrationBean = new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        registrationBean.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());//大于0，tomcat启动时立即初始化
        return registrationBean;
    }

    @Bean //使用默认的RequestMappingHandlerMapping并不会放到spring容器中，无法取出
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        return new RequestMappingHandlerMapping();
    }

    @Bean
    public MyRequestMappingHandlerAdapter requestMappingHandlerAdapter(){
        MyRequestMappingHandlerAdapter handlerAdapter = new MyRequestMappingHandlerAdapter();
        RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter()));
        handlerAdapter.setCustomArgumentResolvers(List.of(requestResponseBodyMethodProcessor));
        return handlerAdapter;
    }

}
