package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.shared.types.plugin.ClassFileObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
public class InMemoryClassLoader extends ClassLoader {
    //pollution
    private final Map<String, ClassFileObject> compiledClasses;
    private final Map<String, Class<?>> loadedClasses = new ConcurrentHashMap<>();
    public InMemoryClassLoader(){
        this(null, Thread.currentThread().getContextClassLoader());
    }
    public InMemoryClassLoader(Map<String, ClassFileObject> compiledClasses, ClassLoader parent) {
        super(parent);
        this.compiledClasses = compiledClasses!=null?compiledClasses:new ConcurrentHashMap<>();
    }
    public InMemoryClassLoader(Map<String, ClassFileObject> compiledClasses) {
        this(compiledClasses, Thread.currentThread().getContextClassLoader());
    }
    public void clear(){
        loadedClasses.clear();compiledClasses.clear();
    }
    public Class<?> forName(String name) {
        return loadedClasses.get(name);
    }
    public void remove(String name){
        loadedClasses.remove(name);
        compiledClasses.remove(name);
    }
    public void load(ClassFileObject cob){
        compiledClasses.put(cob.getClassName(),cob);
    }
    public Class<?> addClass(ClassFileObject compiledClass) throws ClassNotFoundException {
        Class<?> cls = loadedClasses.get(compiledClass.getClassName());
        if (cls!= null) return cls;
        var bytes = compiledClass.getClassBytes();
        cls = defineClass(compiledClass.getClassName(), bytes, 0, bytes.length);
        loadedClasses.put(cls.getName(), cls);
        return cls;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        var cls = loadedClasses.get(name);
        if (cls!=null) return cls;
        ClassFileObject fileObject = compiledClasses.get(name);
        if (fileObject != null) {
            byte[] bytes = fileObject.getClassBytes();
            var claz = defineClass(name, bytes, 0, bytes.length);
            loadedClasses.put(claz.getName(), claz);
            return claz;
        }
        return super.findClass(name);
    }
}
