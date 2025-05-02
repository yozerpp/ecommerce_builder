package me.yusuf.ecommerce_builder.shared.components;

import lombok.Getter;
import me.yusuf.ecommerce_builder.shared.types.plugin.MethodMetadata;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class MethodMetadataRegistry {
    private final Map<String, Map<String, MethodMetadata>> classAndMethodsMap;
    private final Class<?>[] entityClasses;

    public MethodMetadataRegistry(String packageName) {
        entityClasses = Arrays.stream(getClasses(packageName)).filter(c -> Modifier.isPublic(c.getModifiers())).toArray(Class[]::new);
        classAndMethodsMap = Arrays.stream(entityClasses).collect(Collectors.toMap(Class::getSimpleName,
                e-> Arrays.stream(e.getDeclaredMethods()).collect(Collectors.toMap(Method::getName,
                m-> new MethodMetadata(m.getGenericReturnType(), m.getGenericParameterTypes(), m.getGenericExceptionTypes(), m.getAnnotation(MethodInfo.class))))));
    }
    public MethodMetadataRegistry(Map<String, Map<String, MethodMetadata>> mData, Class<?>[] classes) {
        this.classAndMethodsMap = mData;
        this.entityClasses = classes;
    }
    private static Class<?>[] getClasses(final String packageName){
        var resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
           resources = resolver.getResources("classpath:" + ClassUtils.convertClassNameToResourcePath(packageName) + "/*.class");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Arrays.stream(resources).map(r->{
            try {
                return extractClassName(r,packageName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).map(s->{
            try {
                return Class.forName(s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Class[]::new);
    }
    private static String extractClassName(Resource resource, String basePackage) throws IOException {
        String resourcePath = resource.getURL().toString();
        String classPath = ClassUtils.convertClassNameToResourcePath(basePackage);
        int startIndex = resourcePath.indexOf(classPath);

        if (startIndex == -1) {
            throw new IOException("Could not extract class name from " + resourcePath);
        }

        String className = resourcePath.substring(startIndex)
                .replace("/", ".")
                .replace(".class", "");

        return className;
    }
    public Map<String, MethodMetadata> getMethods(String className){
        return classAndMethodsMap.get(className);
    }

}
