package me.yusuf.ecommerce_builder.demo.engine.repository;

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
    private final RepositoryProxyFactory repositoryProxyFactory;
    private final Class<?> entityClass;
    public RepositoryFacade(Class<? extends Repository<?,?>> iface, Class<?> entityClass, Repository<?,?> defaultRepository, RepositoryProxyFactory repositoryProxyFactory) {
        this.defaultRepository = defaultRepository;
        this.iface = iface;
        this.entityClass = entityClass;
        this.repositoryProxyFactory = repositoryProxyFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        Repository<?,?> dynamicRepository = repositoryProxyFactory.create(EditorIdContextHolder.getEditorId(),iface,entityClass);
        if (method.getName().matches("^(get|find).*$") || method.isAnnotationPresent(Query.class) && !method.isAnnotationPresent(Modifying.class)) {
            var dynRes = method.invoke(dynamicRepository, args);
            return switch (dynRes){
                case Optional o -> o.isPresent()?o:method.invoke(defaultRepository, args);
                case Collection l -> {
                    l.addAll((Collection)  method.invoke(defaultRepository, args));
                    yield l;
                }
                case Page p -> {
                    var res = new ArrayList(p.getContent());
                    res.addAll((Collection)  method.invoke(defaultRepository, args));
                    yield  new PageImpl(res,p.getPageable(),p.getTotalElements());
                }
                case null -> method.invoke(defaultRepository, args);
                default -> dynRes;
            };
        } else{
            return method.invoke(dynamicRepository, args);
        }
    }


}
