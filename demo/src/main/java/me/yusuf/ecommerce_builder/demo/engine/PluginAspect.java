package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.demo.utils.exception.ContextedException;
import me.yusuf.ecommerce_builder.shared.components.EditorContextHolder;
import me.yusuf.ecommerce_builder.shared.types.PluginMetadata;
import me.yusuf.ecommerce_builder.shared.types.tuple.ITuple1;
import me.yusuf.utils.ReflectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

@Aspect
@Component
//@ConditionalOnProperty(prefix = "deployment", name = "type", havingValue = "editor")
public class PluginAspect {
    private final PluginRegistry pluginRegistry;
    public PluginAspect(PluginRegistry pluginRegistry){
        this.pluginRegistry = pluginRegistry;
    }
    @Pointcut("execution(public * me.yusuf.ecommerce_builder.demo.service.*.*(..))")
    public void ServiceMethods() {}

    @AfterReturning(pointcut = "ServiceMethods()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        var pluginAndMetadataArray = pluginRegistry.getPluginsAfterMethod(EditorContextHolder.getEditorId(), ((MethodSignature)joinPoint.getSignature()).getMethod());
        for (var pluginAndMetadata : pluginAndMetadataArray){
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            var metaData = pluginAndMetadata.metadata();
            var plugin = pluginAndMetadata.method();
            var fits = findFits(metaData, method);
            var args = getArgs((ITuple1<?>) result,fits, metaData.argTypes().length);
            try {
                plugin.invoke(null,args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @AfterThrowing(pointcut = "ServiceMethods()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, ContextedException ex) {
        var pluginAndMetadataArray = pluginRegistry.getPluginsAfterMethod(EditorContextHolder.getEditorId(),((MethodSignature)joinPoint.getSignature()).getMethod());
        for(var pluginAndMetadata: pluginAndMetadataArray) {
            var metaData = pluginAndMetadata.metadata();
            var plugin = pluginAndMetadata.method();
            var fits = findFits(metaData, plugin);
            var args = getArgs( ex.context,fits,metaData.argTypes().length);
            try {
                plugin.invoke(null, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static Object[] getArgs(ITuple1<?> argTuple, Boolean[] fits, int len){
        var args = new Object[len];
        var tplCls = argTuple.getClass();
        for (int i=0, j = 0; i< args.length; i++) {
            try {
                if (fits[i]) args[j++] = tplCls.getField('_' + String.valueOf(i)).get(argTuple);
            }catch (IllegalAccessException | NoSuchFieldException _) {}
        }
        return args;
    }
    private static Boolean[] findFits(PluginMetadata pluginMetadata, Method joinPoint) {
        if (joinPoint.getGenericReturnType() instanceof ParameterizedType){
            var tpargs = ((ParameterizedType)joinPoint.getGenericReturnType()).getActualTypeArguments();
            Boolean[] ret = new Boolean[tpargs.length];
            for (int i=0; i<tpargs.length; i++) ret[i] = false;
            int i=0, j= 0;
            for (var type : tpargs){
                if (pluginMetadata.argTypes().length ==j) break;
                if( ReflectionUtils.isDeepAssignableFrom(type,pluginMetadata.argTypes()[j])){
                    j++;
                    ret[i] = true;
                }
                i++;
            }
            return ret;
        }
        else if (pluginMetadata.argTypes().length>1)
            return Arrays.stream(pluginMetadata.argTypes()).map(s->false).toArray(Boolean[]::new);
        else if(joinPoint.getGenericReturnType().equals(Void.TYPE)) return new Boolean[]{pluginMetadata.argTypes().length==0};
        else return new Boolean[]{((Class<?>)pluginMetadata.argTypes()[0]).isAssignableFrom(joinPoint.getReturnType())};
    }
}
