package me.yusuf.ecommerce_builder.demo.engine.repository;

import me.yusuf.ecommerce_builder.shared.components.EditorIdContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class RepositoryFacade implements InvocationHandler {
    private final Repository<?,?> secondaryRepository;
    private final Class<? extends Repository<?,?>> iface;
    private final RepositoryProxyFactory repositoryProxyFactory;
    private final Class<?> entityClass;
    public RepositoryFacade(Class<? extends Repository<?,?>> iface, Class<?> entityClass, Repository<?,?> secondaryRepository, RepositoryProxyFactory repositoryProxyFactory) {
        this.secondaryRepository = secondaryRepository;
        this.iface = iface;
        this.entityClass = entityClass;
        this.repositoryProxyFactory = repositoryProxyFactory;
    }

    @Override
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        Repository<?,?> primaryRepository = repositoryProxyFactory.create(EditorIdContextHolder.getEditorId(),iface,entityClass);
        if (method.getName().matches("^(get|find).*$") || method.isAnnotationPresent(Query.class) && !method.isAnnotationPresent(Modifying.class)) {
            var res = method.invoke(primaryRepository, args);
            if (res == null || res instanceof List<?> l&& l.isEmpty() || res instanceof Page<?> p && p.isEmpty() || res instanceof Optional<?> o && o.isEmpty())
                return method.invoke(secondaryRepository, args);
            else return res;
        } else{
            return method.invoke(primaryRepository, args);
        }
    }


}
