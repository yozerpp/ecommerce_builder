package me.yusuf.ecommerce_builder.demo.engine.repository;

import lombok.Getter;
import me.yusuf.utils.ReflectionUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class EntityProxy implements MethodInterceptor {
    @Getter
    private final Set<Field> dirtMap = new HashSet<>();
    private final Object entity;

    public EntityProxy(Object entity) {
        this.entity = entity;
    }
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.getName().startsWith("set")) {
            method.invoke(entity, args);
            dirtMap.add(ReflectionUtils.getField(method));
            return null;
        } else if(method.getName().startsWith("get")){
            return method.invoke(entity, args);
        } else return method.invoke(entity,args);
    }
}
