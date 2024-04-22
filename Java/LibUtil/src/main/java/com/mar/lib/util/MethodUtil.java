package com.mar.lib.util;

import java.lang.reflect.Method;

public class MethodUtil {
    public static Method getMethod(Class<?> target,String name, Class<?>... parameterTypes){
        try {
            Method method = target.getDeclaredMethod(name,parameterTypes);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Class<?> superClass = target.getSuperclass();
            if(superClass==null)
                return null;
            else
                return getMethod(superClass,name,parameterTypes);
        }
    }
}
