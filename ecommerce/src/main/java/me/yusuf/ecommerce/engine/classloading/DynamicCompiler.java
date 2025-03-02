package me.yusuf.ecommerce.engine.classloading;

import javax.tools.*;
import java.io.IOException;
import java.util.*;

public interface DynamicCompiler {
    String PLUGIN_PACKAGES = "me.yusuf.ecommerce.engine.plugin";
// TODO: run imports
    static Class<?> compile(String[] imports, String className, String sourceCode) {
        StringBuilder sourceCodeBuilder = new StringBuilder("package ").append(PLUGIN_PACKAGES).append(";\n");
        if (imports != null) {
            for (String imp : imports) {
                sourceCodeBuilder.append("import ").append(imp).append(";\n");
            }
        }
        sourceCodeBuilder.append("public class " + className + "{\n"+sourceCode+ "\n}");
        sourceCode = sourceCodeBuilder.toString();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        InMemoryClassLoader classLoader = new InMemoryClassLoader(new HashMap<>());
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);

        // Map to hold the compiled class files
        final InMemoryClassFileObject[] compiledClass = new InMemoryClassFileObject[1];

        // Custom file manager to capture the compiled classes
        JavaFileManager fileManager = new ForwardingJavaFileManager<JavaFileManager>(standardFileManager) {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                       JavaFileObject.Kind kind, FileObject sibling) {
                InMemoryClassFileObject fileObject = new InMemoryClassFileObject(className, kind);
                return compiledClass[0] = fileObject;
            }
//            @Override
//            public Iterable<JavaFileObject> list(Location loc, String pkg, Set<JavaFileObject.Kind> kinds, boolean rec) throws IOException {
//                return Arrays.asList(compiledClass);
//            }
//            @Override
//            public String inferBinaryName(Location loc, JavaFileObject file) {
//                if (file instanceof InMemoryJavaFileObject f) return f.getName();
//                else return super.inferBinaryName(loc, file);
//            }
        };

        InMemoryJavaFileObject sourceObject = new InMemoryJavaFileObject(PLUGIN_PACKAGES + '.' + className, sourceCode);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null,
                List.of(sourceObject));
        boolean success = task.call();

        try{
            fileManager.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        if (!success) {
            throw new RuntimeException("Compilation failed.");
        }
        try {
            return classLoader.addClass(compiledClass[0]);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

