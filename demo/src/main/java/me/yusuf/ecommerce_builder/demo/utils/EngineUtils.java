package me.yusuf.ecommerce_builder.demo.utils;

import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.repository.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public interface EngineUtils {
    public static Optional<Class<?>> extractEntityClassFromRepository(Class<?> r){
        return Arrays.stream(r.getGenericInterfaces())
                .filter(c->c instanceof ParameterizedType pt && pt.getRawType() instanceof Class<?> rc&& Repository.class.isAssignableFrom(rc) && pt.getActualTypeArguments().length==2)
                .findAny().map(ParameterizedType.class::cast).map(p->(Class<?>)p.getActualTypeArguments()[0]);
    }
    public static Optional<Class<? extends Repository<?,?>>> getRepositoryForEntityClass(Class<?> entityClass, Collection<Class<? extends Repository<?,?>>> repositoryInterfaces){
        return repositoryInterfaces.stream().filter(r->entityClass.equals(extractEntityClassFromRepository(r).orElse(null))).findAny();
    }
    public static Class<?> stripProxyClass(Class<?> proxyOrEntityClass){
        return HibernateProxy.class.isAssignableFrom(proxyOrEntityClass)?
                proxyOrEntityClass.getSuperclass()
                :proxyOrEntityClass;
    }

    /**
     * @param dynamicEntityClass cannot be proxy.
     * @return
     */
    public static Class<?> getBaseEntityClass(Class<?> dynamicEntityClass){
        return dynamicEntityClass.getSuperclass();
    }
    public static boolean isDynamicEntityClass(Class<?> cls){
        return cls.getPackageName().matches(EntitySource.DYNAMIC_PACKAGE_PREFIX + "\\d+");
    }
}
