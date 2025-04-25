package me.yusuf.ecommerce_builder.editor.compiler;

import me.yusuf.ecommerce_builder.shared.types.ClassFileObject;

import javax.tools.*;
import java.io.IOException;
import java.util.*;

public interface DynamicCompiler {
    String PLUGIN_PACKAGES = "me.yusuf.ecommerce.engine.method";
// TODO: run imports
    static ClassFileObject compile(String className, String sourceCode) {
        //TODO: add editorId as a prefix to this class.
        sourceCode = "package " + PLUGIN_PACKAGES + ";\n" + sourceCode;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        // Map to hold the compiled class files
        final ClassFileObject[] compiledClass = new ClassFileObject[1];
        // Custom file manager to capture the compiled classes
        JavaFileManager fileManager = new ForwardingJavaFileManager<JavaFileManager>(standardFileManager) {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                       JavaFileObject.Kind kind, FileObject sibling) {
                ClassFileObject fileObject = new ClassFileObject(className, kind);
                return compiledClass[0] = fileObject;
            }
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
        return compiledClass[0];
    }
}
