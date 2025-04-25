package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.shared.types.MethodMetadata;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodMetadataAnn;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import me.yusuf.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class MethodMetadataRegistry {
    private final Map<Method, MethodMetadata> methodAndMetadataMap;
    private final Map<MethodMetadata, Method> metadataAndMethodMap;
    private final Class<?>[] entityClasses;

    public MethodMetadataRegistry(String packageName) {
        entityClasses = ReflectionUtils.getClasses(packageName).stream().filter(c -> Modifier.isPublic(c.getModifiers())).toArray(Class[]::new);
        methodAndMetadataMap = Arrays.stream(entityClasses).map(Class::getMethods).flatMap(Arrays::stream).collect(Collectors.toMap(m -> m, m ->
                new MethodMetadata(m.getGenericReturnType(), m.getGenericParameterTypes(), m.getGenericExceptionTypes(), m.getAnnotation(MethodMetadataAnn.class))
        ));
        metadataAndMethodMap = methodAndMetadataMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public MethodMetadataRegistry(Map<Method, MethodMetadata> mData, Class<?>[] classes) {
        this.methodAndMetadataMap = mData;
        this.entityClasses = classes;
        metadataAndMethodMap = methodAndMetadataMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public Tuple2<Method, MethodMetadata> getMethodAndMetadata(String description, String clsName) {
        return metadataAndMethodMap.entrySet().stream().filter(e -> e.getKey().annotation().entity().getSimpleName().equals(clsName) && e.getKey().annotation().methodFriendlyName().equals(description)).findFirst().map(e -> new Tuple2<>(e.getValue(), e.getKey())).orElse(null);
    }

    public Tuple2<Method, MethodMetadata> getMethodAndMetadata(String description, Class<?> cls) {
        var method = metadataAndMethodMap.get(new MethodMetadata(null, null, null, new MethodMetadataAnn.Impl(description, cls)));
        return new Tuple2<>(method, methodAndMetadataMap.get(method));
    }

    public Tuple2<Method, MethodMetadata>[] getMethods() {
        return metadataAndMethodMap.entrySet().stream().map(e -> new Tuple2<>(e.getKey(), e.getValue())).toArray(Tuple2[]::new);
    }

    public Tuple2<Method, MethodMetadata>[] getMethods(String className) throws ClassNotFoundException {
        return methodAndMetadataMap.entrySet().stream().filter(e -> e.getValue().annotation().entity().getSimpleName().equals(className))
                .toArray(Tuple2[]::new);
    }

    public Tuple2<Method, MethodMetadata>[] getMethods(Class<?> clazz) {
        return methodAndMetadataMap.entrySet().stream().filter(e -> e.getValue().annotation().entity().equals(clazz))
                .toArray(Tuple2[]::new);
    }

    /**
     * @return a tuple contain both the classes and the map of methods and metadatas
     */
    public Tuple2<Class<?>[], Map<Method, MethodMetadata>> getAll() {
        return new Tuple2<>(entityClasses, methodAndMetadataMap);
    }

    public Class<?>[] getEntityClasses() {
        return entityClasses;
    }
}
