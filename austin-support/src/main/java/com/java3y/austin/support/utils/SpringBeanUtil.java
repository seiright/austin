package com.java3y.austin.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * SpringBean工具类
 * <p>对SpringBeanFactory方法再次封装
 * <p>【注意】不能在优先级更高的获取bean方式中使用。比如{@link PostConstruct}
 * @author zhaolifeng
 * @version 1.0
 * @description:
 * @date 2022/10/7 14:33
 */
@Component
@Slf4j
public class SpringBeanUtil implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 获取上下文对象实例
     * @return 上下文对象实例
     * @author zhaolifeng
     * @date 2022/10/7 14:40
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 根据bean名称获取bean对象
     * @param name bean名称
     * @return bean对象
     * @author zhaolifeng
     * @date 2022/10/7 14:41
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 根据bean类型获取bean对象
     * @param clazz bean类型
     * @return bean对象
     * @author zhaolifeng
     * @date 2022/10/7 14:41
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 根据bean名称和类型获取bean对象
     * @param name bean名称
     * @param clazz bean类型
     * @return bean对象
     * @author zhaolifeng
     * @date 2022/10/7 14:41
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        try {
            return getApplicationContext().getBean(name, clazz);
        } catch (BeansException e) {
            log.error("SpringBeanUtil#getBean error: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据注解类型获取beanMap
     * @param annotationType 注解类型
     * @return beanMap
     * @author zhaolifeng
     * @date 2022/10/7 14:50
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType){
        return getApplicationContext().getBeansWithAnnotation(annotationType);
    }
}
