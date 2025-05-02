package me.yusuf.ecommerce_builder.editor.tool.compiler;

import me.yusuf.ecommerce_builder.editor.EditorApplication;
import me.yusuf.ecommerce_builder.shared.types.plugin.ClassFileObject;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class DynamicCompiler {
    public record SourceFile(String name/*neeeds to be fqcn*/, String content){}
    public static List<ClassFileObject> compile(SourceFile[] sourceFiles) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        // Map to hold the compiled class files
        final List<ClassFileObject> compiledClasses = Collections.synchronizedList(new ArrayList<>(sourceFiles.length));
        // Custom file manager to capture the compiled classes
        JavaFileManager fileManager = new ForwardingJavaFileManager<JavaFileManager>(standardFileManager) {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                       JavaFileObject.Kind kind, FileObject sibling) {
                ClassFileObject fileObject = new ClassFileObject(className, kind);
                compiledClasses.add(fileObject);
                return fileObject;
            }
        };
        var sources = Arrays.stream(sourceFiles).map(s->new InMemoryJavaFileObject(s.name, s.content)).toList();
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null,
                List.of("-classpath", classPathString,
                        "-processor", "lombok.launch.AnnotationProcessorHider$AnnotationProcessor"),
                Arrays.stream(sourceFiles).map(s->s.name).toList(),
                sources);
        boolean success = task.call();
        try{
            fileManager.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        if (!success) {
            throw new RuntimeException("Compilation failed.");
        }
        return compiledClasses;
    }
    private static final String classPathString;
    static {
        if (EditorApplication.isKUBE_DEPLOYMENT()) {
            try {
                var thisJar = new File(System.getProperty("java.class.path"));
//            var moduleJar = extractResources(null,thisJar,"BOOT-INF/lib/shared-0.0.1-SNAPSHOT.jar" ).get(0);
                var classPathDir = Files.createTempDirectory("classpath-extracts");
                classPathDir.toFile().deleteOnExit();
                extractResources(classPathDir, thisJar, null);
                classPathString = buildClassPathString(classPathDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else
            classPathString = System.getProperty("java.class.path");
    }
    private static List<File> extractResources(Path dir,File jarFile, String resourcePath) throws IOException {
        List<File> extracted = new ArrayList<>();

        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.isDirectory()) continue;

                boolean match = (resourcePath == null) || entry.getName().equals(resourcePath);
                if (!match) continue;
                Path temp;
                if (dir==null)
                    temp = Files.createTempFile("extracted-", "-" + Paths.get(entry.getName()).getFileName());
                else temp = Files.createTempFile(dir,"extracted-","-" + Paths.get(entry.getName()).getFileName());
                temp.toFile().deleteOnExit();

                try (InputStream is = jar.getInputStream(entry);
                     OutputStream os = Files.newOutputStream(temp)) {
                    is.transferTo(os);
                }

                extracted.add(temp.toFile());

                // If resourcePath was set, no need to check further
                if (resourcePath != null) break;
            }
        }
        return extracted;
    }

    private static String buildClassPathString(Path dir) {
        StringBuilder classpath = new StringBuilder();

        File[] jars = dir.toFile().listFiles((_, name) -> name.endsWith(".jar"));
        if (jars != null) {
            for (File jar : jars) {
                classpath.append(jar.getAbsolutePath()).append(File.pathSeparator);
            }
        }

        return classpath.toString();
    }}
