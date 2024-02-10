package com.cheese.context;

public class BaseContext
{

    public static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static void setCurrentId(String id) {
        threadLocal.set(id);
    }

    public static Object getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
