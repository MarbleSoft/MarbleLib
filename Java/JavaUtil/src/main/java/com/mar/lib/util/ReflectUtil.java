package com.mar.lib.util;

import java.lang.reflect.Constructor;

public class ReflectUtil {
    public static <T> T newInstanse(Class<T>  cls, Object... initargs){
        Constructor<T> constructor = null;
        Class<?>[] parameterTypes = null;
        if(initargs!=null){
            int len = initargs.length;
            parameterTypes = new Class<?>[len];
            for(int i = 0; i< len; i++){
                parameterTypes[i] = initargs[i].getClass();
            }
        }
        try{
            constructor = cls.getDeclaredConstructor(parameterTypes);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
            try{
                constructor = cls.getDeclaredConstructor();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }catch (SecurityException e){
            e.printStackTrace();
            try{
                constructor = cls.getConstructor(parameterTypes);
            }catch (NoSuchMethodException e2){
                e2.printStackTrace();
                try{
                    constructor = cls.getConstructor();
                }catch (Exception e3){
                    e3.printStackTrace();
                }
            }catch (SecurityException e2){
                e2.printStackTrace();
            }
        }
        if(constructor==null)
            return null;
        try {
            if(constructor.getParameterCount()>0){
                return constructor.newInstance(initargs);
            }else {
                return constructor.newInstance();
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
