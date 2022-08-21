package com.kun.rpc04.server.spring;

import com.kun.rpc04.server.utils.ServiceProvider;
import com.kun.rpc04.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * call this method before creating the bean to see if the class is annotated
 *
 * @author shuang.kou
 * @createTime 2020年07月14日 16:42:00
 */
@Slf4j
@Component
public class SpringServerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            RpcService annotation = bean.getClass().getAnnotation(RpcService.class);
            ServiceProvider.processor(annotation.name(),bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
