package me.yusuf.ecommerce_builder.demo.engine;

import jakarta.persistence.Entity;
import lombok.Getter;
import me.yusuf.ecommerce_builder.shared.types.plugin.ClassFileObject;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import me.yusuf.ecommerce_builder.shared.utils.SharedUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class EntityRegistry {
    private static final String ENTITY_PACKAGE_PREFIX = "me.yusuf.ecommerce_builder.demo.domain.dynamic.entity.editor";
    @Getter
    private final Class<?>[] defaultTypes;
    private final Map<Integer, Map<Class<?>, Class<?>>> registry = new HashMap<>();
    private final InMemoryClassLoader classLoader;
    public EntityRegistry(InMemoryClassLoader classLoader, Class<?>[] defaultEntityClasses) {
        this.classLoader = classLoader;
        this.defaultTypes = defaultEntityClasses;
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
    public Collection<Class<?>> registerAll(List<EntitySource> sources){
        var grouped = sources.stream().collect(Collectors.groupingBy(s->s.getId().editorId()));
        return grouped.entrySet().stream().map(e->{
            var map = registry.computeIfAbsent(e.getKey(),_->new HashMap<>());
            e.getValue().stream().map(s->new Tuple2<>(s.getId().entityClass(),new ClassFileObject(EntitySource.getClassName(s),s.getByteEncoded())))
                    .peek(t->classLoader.load(t._2()))
                    .toList().forEach(t-> {
                        try {
                            map.put(t._1(),classLoader.addClass(t._2()));
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
            return map.values();
        }).flatMap(Collection::stream).toList();
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
        var allClasses = new ArrayList<>(Arrays.asList(defaultTypes));
        var modified = getAllModified(editorId);
        allClasses.replaceAll(cls-> modified.stream().filter(modifiedCls->
                modifiedCls.getSimpleName().replaceAll("_v\\d+$","").equals(cls.getSimpleName())).findAny().orElse(cls)
        );
        return allClasses;
    }
    public Collection<Class<?>> getAllModified(int editorId){
        return registry.computeIfAbsent(editorId, _->new HashMap<>()).values();
    }
    public Class<?> get(int editorId, Class<?> clazz){
        return registry.computeIfAbsent(editorId, _->new HashMap<>()).get(clazz);
    }
}
