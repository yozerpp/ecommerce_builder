package me.yusuf.ecommerce.engine.classloading;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryClassLoader extends ClassLoader {
    private final Map<String, InMemoryClassFileObject> compiledClasses;
    private final Set<Class<?>> loadedClasses = new HashSet<>();
    public InMemoryClassLoader(Map<String, InMemoryClassFileObject> compiledClasses, ClassLoader parent) {
        super(parent);
        this.compiledClasses = compiledClasses;
    }
    public InMemoryClassLoader(Map<String, InMemoryClassFileObject> compiledClasses) {
        this(compiledClasses, Thread.currentThread().getContextClassLoader());
    }

    protected Class<?> addClass(InMemoryClassFileObject compiledClass) throws ClassNotFoundException {
        var bytes = compiledClass.getClassBytes();
        var cls = defineClass(compiledClass.getClassName(), bytes, 0, bytes.length);
        loadedClasses.add(cls);
        return cls;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InMemoryClassFileObject fileObject = compiledClasses.get(name);
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
