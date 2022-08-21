package com.kun.rpc08.annotation;


import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC reference annotation, autowire the service implementation class
 *
 * @author smile2coder
 * @createTime 2020年09月16日 21:42:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Component
public @interface RpcReference {
    String name();
}
