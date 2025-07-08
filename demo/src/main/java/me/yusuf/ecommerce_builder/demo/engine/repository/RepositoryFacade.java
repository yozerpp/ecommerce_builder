package me.yusuf.ecommerce_builder.demo.engine.repository;

import me.yusuf.ecommerce_builder.demo.engine.EntityRegistry;
import me.yusuf.ecommerce_builder.shared.components.EditorIdContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class RepositoryFacade implements InvocationHandler {
    private final Repository<?,?> defaultRepository;
    private final Class<? extends Repository<?,?>> iface;
    private final RepositoryFactory repositoryFactory;
    private final EntityRegistry entityRegistry;
    public RepositoryFacade(EntityRegistry entityRegistry, Class<? extends Repository<?,?>> iface, Repository<?,?> defaultRepository, RepositoryFactory repositoryFactory) {
        this.entityRegistry = entityRegistry;
        this.defaultRepository = defaultRepository;
        this.iface = iface;
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        Repository<?,?> dynamicRepository = repositoryFactory.create(EditorIdContextHolder.getEditorId(),iface);
        if (method.getName().matches("^(get|find).*$") || method.isAnnotationPresent(Query.class) && !method.isAnnotationPresent(Modifying.class)) {
            var dynRes = method.invoke(dynamicRepository, args);
            var v = switch(dynRes){
                case Optional o -> o.isPresent()?o:method.invoke(defaultRepository, args);
                case Collection l -> {
                    l.addAll((Collection)  method.invoke(defaultRepository, args));
                    yield l;
                }
                case Page p -> {
                    var res = new ArrayList(p.getContent());
                    res.addAll((Collection)  method.invoke(defaultRepository, args));
                    yield new PageImpl(res,p.getPageable(),p.getTotalElements());
                }
                case null -> method.invoke(defaultRepository, args);
                default -> dynRes;
            };
        } else{
            return method.invoke(dynamicRepository, args);
        }
    }
}
