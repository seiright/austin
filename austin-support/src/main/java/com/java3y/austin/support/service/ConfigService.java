package com.java3y.austin.support.service;


/**
 * 读取配置服务
 * @description:
 * @author zhaolifeng
 * @date 2022/10/6 21:04
 * @version 1.0
 */
public interface ConfigService {

    /**
     * 读取配置
     * <p>1、当启动使用了apollo或者nacos, 优先读取远程配置(前者优先级更高)
     * <p>2、当没有启动远程配置，读取本地 local.properties 配置文件的内容
     * @param key 指定配置的key
     * @param defaultValue 若找不到key, 需返回的默认值.
     * @return key对应的值
     */
    String getProperty(String key, String defaultValue);

}
