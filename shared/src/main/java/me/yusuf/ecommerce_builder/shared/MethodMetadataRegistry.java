package me.yusuf.ecommerce_builder.shared;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class MethodMetadataRegistry {
    String packageName;
    private static List<Class<?>> getClasses(String packageName) {
        //implement this without reflections package, AI!
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
        return allClasses.stream()
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .collect(Collectors.toList());
    }
}
