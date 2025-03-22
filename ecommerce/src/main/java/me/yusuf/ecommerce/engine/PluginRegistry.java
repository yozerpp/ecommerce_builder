package me.yusuf.ecommerce.engine;

import me.yusuf.ecommerce_builder.shared.types.Plugin;
import me.yusuf.ecommerce_builder.shared.types.PluginMetadata;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
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
    Map<Tuple2<Integer, Method>, Plugin> plugins = new HashMap<>();

    static void registerPlugin(int userId, Method after, Plugin plugin) throws IncompatibleMethodSignatureException {
        if (!checkCompatible(plugin.metadata())) throw new IncompatibleMethodSignatureException(after.getGenericReturnType(), plugin.metadata().argTypes());
        plugins.put(new Tuple2<>(userId, after),plugin);
    }
    static void registerPlugin(int userId, String afterkey, Plugin plugin) throws NotFoundException, IncompatibleMethodSignatureException {
        var method =getMethodForKey(afterkey);
        registerPlugin(userId, method, plugin);
    }
    static boolean checkCompatible(PluginMetadata metadata){
        return methodSignatureMatch(metadata.hookedMethod(), metadata.argTypes());
    }
    private static Method getMethodForKey(String key) throws NotFoundException {
        String methodFullName = key.replace("()","");
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
        return method;
    }
    private static boolean methodSignatureMatch(Method hooked, Type[] argTypes){ //match the return type of the hooked method with the arg tpye of the pluıgin
        ParameterizedType retType;
        Type[] retTypes;
        if (Map.Entry.class.isAssignableFrom(hooked.getReturnType()))
            retType =(ParameterizedType)  ((ParameterizedType) hooked.getGenericReturnType()).getActualTypeArguments()[1];  //exception?
        else retType = (ParameterizedType) hooked.getGenericReturnType();
        retTypes = retType.getActualTypeArguments();
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
    static void unregisterPlugin(int userId, Method after) {
        plugins.remove(new Tuple2<>(userId, after));
    }
    static void unregisterPlugin(int userId, String key) throws NotFoundException {
        unregisterPlugin(userId, getMethodForKey(key));
    }
    static Plugin getPlugin(Integer userId, Method afterKey) {
        return  plugins.get(new Tuple2<>(userId, afterKey));
    }
    public static class IncompatibleMethodSignatureException extends Exception{
        public IncompatibleMethodSignatureException(Type return_type, Type[] args) {
            var s  ="unexpected arguement type signature for the plugin. Hooked method's return type is: " + ReflectionUtils.toGenericString(return_type) +
                    " the parameter types of the plugin are: " +  Arrays.stream(args).map(ReflectionUtils::toGenericString).collect(Collectors.joining(","));
            super(s);
        }
    }
}
