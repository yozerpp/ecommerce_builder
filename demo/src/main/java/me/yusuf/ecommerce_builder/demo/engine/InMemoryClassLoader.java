package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.shared.types.ClassFileObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class InMemoryClassLoader extends ClassLoader {
    private final Map<String, ClassFileObject> compiledClasses;
    private Set<Class<?>> loadedClasses = new HashSet<>();
    public InMemoryClassLoader(){
        this(null, Thread.currentThread().getContextClassLoader());
    }
    public InMemoryClassLoader(Map<String, ClassFileObject> compiledClasses, ClassLoader parent) {
        super(parent);
        this.compiledClasses = compiledClasses!=null?compiledClasses:new HashMap<>();
    }
    public InMemoryClassLoader(Map<String, ClassFileObject> compiledClasses) {
        this(compiledClasses, Thread.currentThread().getContextClassLoader());
    }
    protected synchronized void clear(){
        loadedClasses.clear();
    }
    protected synchronized void remove(int startIndex, int endIndex){
        endIndex = endIndex==0?loadedClasses.size():endIndex;
        loadedClasses = loadedClasses.stream().skip(startIndex).limit(endIndex).collect(Collectors.toUnmodifiableSet());
    }
    protected synchronized Class<?> addClass(ClassFileObject compiledClass) throws ClassNotFoundException {
        var bytes = compiledClass.getClassBytes();
        var cls = defineClass(compiledClass.getClassName(), bytes, 0, bytes.length);
        loadedClasses.add(cls);
        return cls;
    }
    @Override
    protected synchronized Class<?> findClass(String name) throws ClassNotFoundException {
        ClassFileObject fileObject = compiledClasses.get(name);
        var cls = loadedClasses.stream().filter(loadedClass -> loadedClass.getName().equals(name)).findFirst();
        if (cls.isPresent()) {
            return cls.get();
        }
        else if (fileObject != null) {
            byte[] bytes = fileObject.getClassBytes();
            var claz = defineClass(name, bytes, 0, bytes.length);
            loadedClasses.add(claz);
            return claz;
        }
        return super.findClass(name);
    }
}
