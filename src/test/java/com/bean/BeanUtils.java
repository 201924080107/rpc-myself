package com.bean;

import com.bean.annotation.RpcService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeanUtils implements BeanPostProcessor {
    @Override
    @SneakyThrows
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)){
            log.debug("添加了注解的bean是{}",bean.getClass().getName());
        }
//        log.debug("{}",bean.getClass().getName());
//        Field[] declaredFields = bean.getClass().getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            Boy annotation = declaredField.getAnnotation(Boy.class);
//            if (null == annotation) {
//                continue;
//            }
//            declaredField.setAccessible(true);
//            try {
//                declaredField.set(bean, annotation.name());
////                log.debug(annotation.name());
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
