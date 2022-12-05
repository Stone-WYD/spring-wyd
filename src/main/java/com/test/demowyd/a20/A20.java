package com.test.demowyd.a20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class A20 {

    private static final Logger log = LoggerFactory.getLogger(A20.class);

    public static void main(String[] args) throws Exception {
        AnnotationConfigServletWebServerApplicationContext context
                = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);

        //1.RequestMappingHandlerMapping 解析@RequestMapping 以及派生注解，生成路径与控制器方法的映射关系，在初始化时生成
        RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        //获取映射结果
/*        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((k,v) ->{
            System.out.println(k + "=" + v);
        } );*/
        //请求来了，获取控制器方法 ,返回结果对HandlerMethod进行了封装->HandlerExecutionChain
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test4");
/*        request.setParameter("name","wyd");
        request.addHeader("token","某个令牌");*/
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        //chain中不光包括了方法信息，还会包含一些拦截器内容
/*        System.out.println("=========");
        System.out.println(chain);*/


        //2.MyRequestMappingHandlerAdapter 解析参数，调用请求方法
        System.out.println("===================>调用请求方法");
        MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);
        handlerAdapter.invokeHandlerMethod(request,response,(HandlerMethod) (chain.getHandler()));
/*        //参数解析器
        System.out.println("===================>所有参数解析器");
        for (HandlerMethodArgumentResolver argumentResolver : handlerAdapter.getArgumentResolvers()) {
            System.out.println(argumentResolver);
        }
        //返回值解析器
        System.out.println("====================>所有返回值解析器");
        for (HandlerMethodReturnValueHandler returnValueHandler : handlerAdapter.getReturnValueHandlers()) {
            System.out.println(returnValueHandler);
        }*/

        //检查响应
        byte[] content = response.getContentAsByteArray();
        System.out.println(new String(content, StandardCharsets.UTF_8));


    }
}
