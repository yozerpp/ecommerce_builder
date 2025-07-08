package me.yusuf.ecommerce_builder.demo.engine.repository;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import me.yusuf.ecommerce_builder.demo.engine.InMemoryClassLoader;
import me.yusuf.ecommerce_builder.demo.utils.Cached;
import me.yusuf.ecommerce_builder.shared.components.DataSourceHolder;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("singleton")
@Getter
public class EntityManagerFactory implements Cached<Integer> {
    private final InMemoryClassLoader inMemoryClassLoader;
    private final DataSourceHolder dataSourceHolder;
    private final Map<Integer, Tuple2<jakarta.persistence.EntityManagerFactory, EntityManager>> cache = new ConcurrentHashMap<>();
    public EntityManagerFactory(DataSourceHolder dataSourceHolder, InMemoryClassLoader inMemoryClassLoader) {
        this.inMemoryClassLoader = inMemoryClassLoader;
        this.dataSourceHolder =dataSourceHolder;
    }
    public EntityManager create(int editorId, List<String> entityClassNames){
        return cache.computeIfAbsent(editorId, id->{
            var emf = this.createInternal(id,entityClassNames);
            return new Tuple2<>(emf,emf.createEntityManager());
        })._2();
    }
    @Override
    public void invalidateCache(Integer editorId){
        var f = cache.remove(editorId);
        if (f!=null) {
            f._2().close();
            f._1().close();
        }
    }
    private jakarta.persistence.EntityManagerFactory createInternal(int editorId, List<String> entityClassNames) {
        var factoryFactory = new LocalContainerEntityManagerFactoryBean();
        factoryFactory.setDataSource(dataSourceHolder.get(editorId));
        factoryFactory.setBeanClassLoader(inMemoryClassLoader);
        String packageName = EntitySource.DYNAMIC_PACKAGE_PREFIX + editorId;
        factoryFactory.setManagedTypes(PersistenceManagedTypes.of(entityClassNames, List.of(packageName)));
        factoryFactory.setPersistenceUnitPostProcessors(pu->{
            pu.setExcludeUnlistedClasses(true);
            var props = new Properties(pu.getProperties());
            props.put(AvailableSettings.CLASSLOADERS, List.of(inMemoryClassLoader));
            pu.setProperties(props);
        });
        factoryFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryFactory.setPersistenceUnitName("editor" + editorId);
        factoryFactory.afterPropertiesSet();
        return factoryFactory.getObject();
    }
}
