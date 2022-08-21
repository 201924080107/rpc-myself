package com.kun.rpc08.client.spring;

import com.kun.rpc08.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * call this method before creating the bean to see if the class is annotated
 *
 * @author shuang.kou
 * @createTime 2020年07月14日 16:42:00
 */
@Slf4j
@Component
public class SpringClientBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    RPCClientProxy rpcClientProxy;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(RpcReference.class)){
                declaredField.setAccessible(true);
                RpcReference annotation = declaredField.getAnnotation(RpcReference.class);
                Object proxy = rpcClientProxy.getProxy(declaredField.getType());
                RPCClientProxy.map.put(declaredField.getType().getName(),annotation.name());
                try {
                    declaredField.set(bean,proxy);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
