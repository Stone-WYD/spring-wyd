package com.test.demowyd.a20;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//经常需要用到请求头中的信息
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface Token {

}
