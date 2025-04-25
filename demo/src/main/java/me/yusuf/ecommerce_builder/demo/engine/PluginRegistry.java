package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.shared.types.Plugin;
import me.yusuf.ecommerce_builder.shared.types.PluginMethod;
import me.yusuf.ecommerce_builder.shared.types.PluginMetadata;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import me.yusuf.utils.ReflectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(scopeName = "singleton")
public class PluginRegistry {
    private final PluginFileRepository repository;
    private final InMemoryClassLoader classLoader;
    private Set<PluginMethod> plugins = Collections.synchronizedSet(new HashSet<>());
    @Value("${demo.plugins.partition-count}")
    private int currentPartitionCount;
    @Value("${demo.plugins.replica-idx}")
    private int replicaIdx;
    public PluginRegistry(PluginFileRepository repository, InMemoryClassLoader classLoader){
        this.repository = repository;
        this.classLoader = classLoader;
    }
    public void registerPlugin(PluginClassFile pluginClassFile){
        try {
            var cls = classLoader.addClass(pluginClassFile.classFile());
            var method = cls.getDeclaredMethod("run");
            var pMethod = new PluginMethod(pluginClassFile.id(), pluginClassFile.metadata(), method);
            plugins.add(pMethod);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public void registerPlugin(int userId, String name, Method after, Method pluginMethod) throws IncompatibleMethodSignatureException {
        if (!methodSignatureMatch(after, pluginMethod.getGenericParameterTypes())) {
            throw new IncompatibleMethodSignatureException(after.getGenericReturnType(), pluginMethod.getGenericParameterTypes());
        }
        var pMethod = new PluginMethod(new Plugin.Id(userId, name, after),
                new PluginMetadata(pluginMethod.getGenericParameterTypes()),
                pluginMethod);
        plugins.add(pMethod);
    }
    public void registerPlugin(int userId, String name, String afterkey, Method pluginMethod) throws NotFoundException, IncompatibleMethodSignatureException {
        Method method = getMethodForKey(afterkey);
        registerPlugin(userId, name, method, pluginMethod);
    }
    public void unregisterPlugin(int userId, String name, String methodKey ) throws NotFoundException {
        unregisterPlugin(new Plugin.Id(userId, name, getMethodForKey(methodKey)));
    }
    public void unregisterPlugin(Plugin.Id id ) {
        plugins.remove(new PluginMethod(id,null,null ));
    }
    public PluginMethod getPluginMethod(Plugin.Id id){
        return plugins.stream().filter(p->p.id().equals(id)).findFirst().orElse(null);
    }
    public PluginMethod[] getPluginsAfterMethod(int userId, String afterKey) throws NotFoundException {
        var method = getMethodForKey(afterKey);
        return getPluginsAfterMethod(userId, method);
    }
    public PluginMethod[] getPluginsAfterMethod(int userId, Method afterKey) {
        return plugins.stream().filter(p->p.id().editorId() == userId && p.id().hookedMethod().equals(afterKey)).sorted(Comparator.comparing(p->p.id().name())).toArray(PluginMethod[]::new);
    }
    public synchronized void partition(int newCount){
        var count = repository.count();
        if (replicaIdx == 0 && currentPartitionCount < newCount) {
            plugins = plugins.stream().limit(count / (currentPartitionCount = newCount)).collect(Collectors.toUnmodifiableSet());
            classLoader.remove(0, (int) (count / currentPartitionCount));
            return;
        }
        var classes=repository.findAll(Pageable.ofSize((int) (count / (currentPartitionCount = newCount))).withPage(replicaIdx));
        classLoader.clear();
        plugins = classes.getContent().stream().map(pf-> {
            try {
                return new PluginMethod(pf.id(),pf.metadata(), classLoader.addClass(pf.classFile()).getDeclaredMethod("run"));
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toUnmodifiableSet());
    }
    private Method getMethodForKey(String key) throws NotFoundException {
        String methodFullName = key.replace("()", "");
        String[] methodNameSplit = methodFullName.split("\\.");
        String className = Arrays.stream(methodNameSplit).limit(methodNameSplit.length - 1)
                .collect(Collectors.joining("."));
        String methodName = methodNameSplit[methodNameSplit.length - 1];
        Method method;
        try {
            var cls = Thread.currentThread().getContextClassLoader().loadClass(className);
            var opt = Arrays.stream(cls.getDeclaredMethods())
                    .filter(m -> m.getName().equals(methodName))
                    .findFirst();
            if (opt.isEmpty()) {
                throw new NotFoundException("No method found with name: " + methodName + " in class: " + className);
            } else {
                method = opt.get();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return method;
    }

    private boolean methodSignatureMatch(Method hooked, Type[] argTypes) { // match the return type of the hooked method with the argument type of the plugin
        ParameterizedType retType;
        Type[] retTypes;
        if (Map.Entry.class.isAssignableFrom(hooked.getReturnType()))
            retType = (ParameterizedType) ((ParameterizedType) hooked.getGenericReturnType()).getActualTypeArguments()[1];
        else
            retType = (ParameterizedType) hooked.getGenericReturnType();
        retTypes = retType.getActualTypeArguments();
        int i = 0, j = 0;
        while (i < argTypes.length) {
            if (j == retTypes.length) {
                return false;
            }
            if (ReflectionUtils.isDeepAssignableFrom(argTypes[i], retTypes[j++])) {
                i++;
            }
        }
        return true;
    }

    public static class IncompatibleMethodSignatureException extends Exception {
        public IncompatibleMethodSignatureException(Type return_type, Type[] args) {
            super("unexpected arguement type signature for the plugin. Hooked method's return type is: " +
                    ReflectionUtils.toGenericString(return_type) +
                    " the parameter types of the plugin are: " +
                    Arrays.stream(args)
                          .map(ReflectionUtils::toGenericString)
                          .collect(Collectors.joining(",")));
        }
    }
}
