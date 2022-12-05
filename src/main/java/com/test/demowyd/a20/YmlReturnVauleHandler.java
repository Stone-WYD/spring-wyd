package com.test.demowyd.a20;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletResponse;

public class YmlReturnVauleHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        Yml methodAnnotation = returnType.getMethodAnnotation(Yml.class);
        return methodAnnotation!=null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        //1.转换结果为yml
        String str = new Yaml().dump(returnValue);
        //2.将yml字符串写入响应
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().println(str);
        //3.设置请求已经处理完毕
        mavContainer.setRequestHandled(true);
    }
}
