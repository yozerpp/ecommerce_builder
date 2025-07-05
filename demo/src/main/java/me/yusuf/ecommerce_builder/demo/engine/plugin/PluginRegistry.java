package me.yusuf.ecommerce_builder.demo.engine.plugin;

import me.yusuf.ecommerce_builder.demo.engine.InMemoryClassLoader;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.shared.types.plugin.IPlugin;
import me.yusuf.ecommerce_builder.shared.types.plugin.Plugin;
import me.yusuf.ecommerce_builder.shared.types.plugin.PluginHandle;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(scopeName = "singleton")
public class PluginRegistry {
    private final InMemoryClassLoader classLoader;
    private final Set<PluginHandle> plugins = Collections.synchronizedSet(new HashSet<>());
//    @Value("${demo.plugins.partition-count}")
//    private int currentPartitionCount;
//    @Value("${demo.plugins.replica-idx}")
//    private int replicaIdx;
    public PluginRegistry( InMemoryClassLoader classLoader){
        this.classLoader = classLoader;
    }
    public void registerPlugin(Plugin plugin){
        try {
            var cls = classLoader.addClass(plugin.classFile());
            var method = cls.getDeclaredMethod("run", Arrays.stream(plugin.getMetadata().argTypes()).map(t->{
                try{
                    return Class.forName(EntitySource.getClassName(t.getTypeName().replaceAll("(\\w+\\.)+",""),plugin.getId().getVersion(),plugin.getId().getEditorId()));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).toArray(Class<?>[]::new));
            var pMethod = new PluginHandle(plugin.id(), plugin.metadata(), method);
            plugins.add(pMethod);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public void unregisterPlugin(IPlugin.Id id){
        plugins.removeIf(p-> p.id().equals(id));
        classLoader.remove(IPlugin.PLUGIN_PACKAGE_PREFIX  + '.' +id.getName()+"Plugin" + "_" + id.getEditorId() + "_v" + id.getVersion());
    }
    public void registerAllPlugins(Iterable<Plugin> plugins){
        for(Plugin plugin : plugins)
            registerPlugin(plugin);
    }
    public PluginHandle[] getPluginsAfterMethod(final int userId,final Method afterKey)  {
        return plugins.stream().filter(p-> {
            try {
                return p.id().getEditorId() == userId && getMethodForKey(p.id().getHookedMethod()).equals(afterKey);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toMap(pm->pm.id().getName(), p->p, (a,b)->a.id().getVersion()>b.id().getVersion()?a:b)).values().toArray(PluginHandle[]::new);
    }
//    public synchronized void partition(int newCount){
//        var count = repository.count();
//        if (replicaIdx == 0 && currentPartitionCount < newCount) {
//            plugins = plugins.stream().limit(count / (currentPartitionCount = newCount)).collect(Collectors.toUnmodifiableSet());
//            classLoader.remove(0, (int) (count / currentPartitionCount));
//            return;
//        }
//        var classes=repository.findAll(Pageable.ofSize((int) (count / (currentPartitionCount = newCount))).withPage(replicaIdx));
//        classLoader.clear();
//        plugins = classes.getContent().stream().map(pf-> {
//            try {
//                return new PluginHandle(pf.id(),pf.metadata(), classLoader.addClass(pf.classFile()).getDeclaredMethod("run"));
//            } catch (ClassNotFoundException | NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//        }).collect(Collectors.toUnmodifiableSet());
//    }
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
                throw new NotFoundException("No handle found with name: " + methodName + " in class: " + className);
            } else {
                method = opt.get();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return method;
    }
}
