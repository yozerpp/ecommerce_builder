package me.yusuf.ecommerce.engine;

import me.yusuf.ecommerce.utils.exception.ContextedException;
import me.yusuf.ecommerce_builder.shared.PluginRegistry;
import me.yusuf.ecommerce_builder.shared.types.Tuple1;
import me.yusuf.utils.ReflectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

@Aspect
@Component
//@ConditionalOnProperty(prefix = "deployment", name = "type", havingValue = "editor")
public class PluginAspect {
    @Pointcut("execution(public * me.yusuf.ecommerce.domain..*Service.*(..))")
    public void ServiceMethods() {}
    @AfterReturning(pointcut = "ServiceMethods()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Tuple1<?> result) {
        var key = joinPoint.getSignature().toString();
        key = key.replaceAll("^\\w+\\s", "");
        var pluginAndMetadata = PluginRegistry.getPlugin(EditorContextHolder.getUserId(), key);
        if (pluginAndMetadata != null) {
            var metaData = pluginAndMetadata.getValue();
            var plugin = pluginAndMetadata.getKey();
            var fits = findFits(metaData, plugin);
            var args = getArgs(metaData.argTypes().length, result,fits);
            try {
                plugin.invoke(null,args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @AfterThrowing(pointcut = "ServiceMethods()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, ContextedException ex) {
        var key = joinPoint.toString().replaceAll("^\\w+\\s", "");
        var pluginAndMetadata = PluginRegistry.getPlugin(EditorContextHolder.getUserId(),key + "->" + ex.exception.getClass().getSimpleName() + ":" + ex.exception.getMessage());
        if (pluginAndMetadata != null) {
            var metaData = pluginAndMetadata.getValue();
            var plugin = pluginAndMetadata.getKey();
            var fits = findFits(metaData, plugin);
            var args = getArgs(metaData.argTypes().length, ex.context,fits);
            try {
                plugin.invoke(null, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static Object[] getArgs(int len,Tuple1<?> argTuple, boolean[] fits){
        var args = new Object[len];
        var tplCls = argTuple.getClass();
        for (int i=0, j = 0; i< args.length; i++) {
            try {
                if (fits[i]) args[j++] = tplCls.getField('_' + String.valueOf(i)).get(argTuple);
            }catch (IllegalAccessException | NoSuchFieldException _) {}
        }
        return args;
    }
    private static boolean[] findFits(PluginRegistry.PluginMetadata pluginMetadata, Method method) {
        var tpargs = ((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments();
        boolean[] ret = new boolean[tpargs.length];
        int i=0, j= 0;
        for (var type : tpargs){
            if( ReflectionUtils.isDeepAssignableFrom(type,pluginMetadata.argTypes()[j])){
                j++;
                ret[i] = true;
            }
            i++;
        }
        return ret;
    }
}
