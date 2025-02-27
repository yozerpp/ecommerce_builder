package me.yusuf.ecommerce.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public interface ReflectionUtils {
    public static Object get(Object object,Field field){
        try {
        var getter= getGetter(object.getClass(),field);
        getter.setAccessible(true);
           return getter.invoke(object);
        } catch (NoSuchMethodException|IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object[] getFields(Object object){
        return Arrays.stream(object.getClass().getDeclaredFields()).map(f-> {
            try {
                return getGetter(object.getClass(), f);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).map(m-> {
            try {
                return m.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }).toArray();
    }
    private static Method getGetter(Class<?> claz , Field field) throws NoSuchMethodException {
       return claz.getMethod((field.getType().equals(boolean.class)?"is":"get") + firstLetterToUpperCase(field.getName()));

    }
    private static String firstLetterToUpperCase(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}
