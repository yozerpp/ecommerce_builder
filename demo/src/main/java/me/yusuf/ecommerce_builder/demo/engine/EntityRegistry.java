package me.yusuf.ecommerce_builder.demo.engine;

import jakarta.persistence.Entity;
import lombok.Getter;
import me.yusuf.ecommerce_builder.shared.types.plugin.ClassFileObject;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.shared.utils.SharedUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Scope("singleton")
public class EntityRegistry {
    private static final String ENTITY_PACKAGE_PREFIX = "me.yusuf.ecommerce_builder.demo.domain.dynamic.entity.editor";
    @Getter
    private static final Collection<Class<?>> defaultTypes;
    private final Map<Integer, Map<Class<?>, Class<?>>> registry = new HashMap<>();
    private final InMemoryClassLoader classLoader;
    public EntityRegistry(InMemoryClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    public Class<?> register(EntitySource source){
        try {
            var c =classLoader.addClass(new ClassFileObject(EntitySource.getClassName(source), source.getByteEncoded()));
            registry.computeIfAbsent(source.getId().editorId(), HashMap::new)
                    .put(source.getId().entityClass(), c);
            return c;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Class<?>[] registerAll(Collection<EntitySource> sources){
        return sources.stream().map(s->new ClassFileObject(EntitySource.getClassName(s),s.getByteEncoded()))
                .peek(classLoader::load)
                .toList().stream().map(cob->{
                    try {
                        return classLoader.addClass(cob);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(Class[]::new);
    }
    public void unregisterOldVersion(EntitySource es){
        if (es.getId().version()<=1) return;
        int editorId = es.getId().editorId();
        var oldId = es.getId();
        es.setId(new EntitySource.Id(es.getId().editorId(),es.getId().entityClass(),es.getId().version()-1));
        classLoader.remove(EntitySource.getClassName(es));
        es.setId(oldId);
    }
    public Collection<Class<?>> getAll(int editorId){
        var allClasses = new ArrayList<>(getDefaultTypes());
        allClasses.replaceAll(cls-> getAllModified(editorId).stream().filter(modifiedCls->
                modifiedCls.getSimpleName().replaceAll("_\\d+$","").equals(cls.getSimpleName())).findAny().orElse(cls)
        );
        return allClasses;
    }
    public Collection<Class<?>> getAllModified(int editorId){
        return registry.computeIfAbsent(editorId, _->new HashMap<>()).values();
    }
    public Class<?> get(int editorId, Class<?> clazz){
        return registry.computeIfAbsent(editorId, _->new HashMap<>()).get(clazz);
    }
    static {
        try {
            defaultTypes = Arrays.stream(SharedUtils.getPatternMatchingClasses("me.yusuf.ecommerce_builder.shared.types.entity.*.java")).filter(c->c.isAnnotationPresent(Entity.class)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
