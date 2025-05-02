package me.yusuf.ecommerce_builder.shared.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface SharedUtils {
    static Class<?>[] getPatternMatchingClasses(String pattern) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        if (pattern.matches("^.*\\.[^*]*$")){
             var i =pattern.lastIndexOf(".");
             pattern = pattern.substring(0,i).replace('.','/') +pattern.substring(i);
        }
        else pattern = pattern.replace('.', '/');
        var resources = resolver.getResources(pattern);
        return Arrays.stream(resources).filter(Resource::isReadable).map(SharedUtils::deriveClassName)
                .map(cname->{
                    try {
                        return Class.forName(cname);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(Class[]::new);
    }
    static List<Class<?>> getAllClasses(final Collection<Class<?>> modified, Class<?>[] original){
        var org = new ArrayList<>(Arrays.asList(original));
        org.replaceAll(c1->modified.stream().filter(c2->c2.getName().replaceAll("_v\\d+$","").equals(c1.getName())).findAny().orElse(c1));
        return org;
    }
    private static String deriveClassName(Resource resource) {
        String url = null; // file:/..., jar:file:/..., etc.
        try {
            url = resource.getURL().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fqcn;
        if (url.startsWith("file:")) //FS Classpath
            fqcn= url.replaceFirst("^.*/classes/", "");
        else //JAR classpath
            fqcn = url.replaceAll(".*!/", "");
        return fqcn.replace('/', '.')
                .replace(".class", "");
    }
}
