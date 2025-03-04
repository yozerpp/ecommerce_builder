package me.yusuf.ecommerce_builder.shared;

import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import me.yusuf.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//@ConditionalOnProperty
public interface PluginRegistry {
    Map<Map.Entry<Integer, String>, Map.Entry<Method, PluginMetadata>> plugins = new HashMap<>();
    static void registerPlugin(Integer userId, String afterkey, Method plugin) throws me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException, IncompatibleMethodSignatureException {
        String methodFullName = afterkey.replace("()","");
        String[] methodNameSplit = methodFullName.split("\\.");
        String className = Arrays.stream(methodNameSplit).limit(methodNameSplit.length-1).collect(Collectors.joining("."));
        String methodName = methodNameSplit[methodNameSplit.length -1];
        Method method;
        try {
            var cls = Thread.currentThread().getContextClassLoader().loadClass(className);
            var opt  = Arrays.stream(cls.getDeclaredMethods()).filter(m->m.getName().equals(methodName)).findFirst();
            if (opt.isEmpty()) throw new NotFoundException("No method found with name: "+ methodName + " in class: "+ className);
            else method=opt.get();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(!methodSignatureMatch(method, plugin)) throw new IncompatibleMethodSignatureException("Method signature is incompatible with the plugin.");
        var metadata = new PluginMetadata(plugin.getGenericParameterTypes(), method);
        plugins.put(Map.entry(userId, afterkey), Map.entry(plugin, metadata));
    }
    private static boolean methodSignatureMatch(Method after, Method plugin){ //match the return type of the after method with the arg tpye of the pluıgin
        ParameterizedType retType;
        Type[] retTypes;
        if (Map.Entry.class.isAssignableFrom(after.getReturnType()))
            retType =(ParameterizedType)  ((ParameterizedType)after.getGenericReturnType()).getActualTypeArguments()[1];  //exception?
        else retType = (ParameterizedType) after.getGenericReturnType();
        retTypes = retType.getActualTypeArguments();
        var argTypes = plugin.getGenericParameterTypes();
        int i = 0, j = 0;
        while (i < argTypes.length){
            if (j == retTypes.length) {
                return false;
            }
            if (ReflectionUtils.isDeepAssignableFrom(argTypes[i], retTypes[j++]))
                i++;
        }
        return true;
    }
    static void unregisterPlugin(Integer userId, Method after) {
        plugins.remove(Map.entry(userId, after));
    }
    static Map.Entry<Method,PluginMetadata> getPlugin(Integer userId, String afterKey) {
        return  plugins.get(Map.entry(userId, afterKey));
    }
    class IncompatibleMethodSignatureException extends Exception{
        public IncompatibleMethodSignatureException(String message) {
            super(message);
        }
    }
    record PluginMetadata(Type[] argTypes, Method hookedMethod) { }
}
