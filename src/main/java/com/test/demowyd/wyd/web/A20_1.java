package com.test.demowyd.wyd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class A20_1 {

    private static final Logger log = LoggerFactory.getLogger(A20_1.class);

    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context
                = new AnnotationConfigServletWebServerApplicationContext(WebConfig_1.class);

        //1.RequestMappingHandlerMapping 解析@RequestMapping 以及派生注解，生成路径与控制器方法的映射关系，在初始化时生成
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        //获取映射结果
        //请求来了，获取控制器方法 ,返回结果对HandlerMethod进行了封装->HandlerExecutionChain
        MockHttpServletRequest request = mockRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        HandlerMethod handler =(HandlerMethod) chain.getHandler();

        //2.MyRequestMappingHandlerAdapter 解析参数，调用请求方法


      DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();

        System.out.println("===================>调用请求方法");
        MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.setArgumentResolvers(List.of(
                new RequestParamMethodArgumentResolver(beanFactory, false),
                new RequestResponseBodyMethodProcessor(List.of(new MappingJackson2HttpMessageConverter()))
        ));
        handlerAdapter.invokeHandlerMethod(request,response, handler);
    }

    private static MockHttpServletRequest mockRequest() {
        //MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test2");
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/myOwnTest");
        request.setContentType("application/json");
        request.setParameter("test1", "wyd's test...");
        request.setParameter("name", "ttt");
        request.setContent("""
                    {
                        "name":"李四",
                        "age":20
                    }
                """.getBytes(StandardCharsets.UTF_8));
        return request;
    }
}
