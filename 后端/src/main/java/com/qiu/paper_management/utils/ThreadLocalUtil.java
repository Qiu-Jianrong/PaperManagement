package com.qiu.paper_management.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal 工具类，封装保证单例模式
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }

    // 因为项目存储的是id和username的claims，所以可以进一步简化
    // 获取id
    public static Integer getId(){
        Map<String, Object> claims = (Map<String, Object>) THREAD_LOCAL.get();
        return (Integer) claims.get("id");
    }
    // 获取username
    public static String getUsername(){
        Map<String, Object> claims = (Map<String, Object>) THREAD_LOCAL.get();
        return (String) claims.get("username");
    }

    //存储键值对
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }


    //清除ThreadLocal 防止内存泄漏
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
