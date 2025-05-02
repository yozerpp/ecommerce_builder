package me.yusuf.ecommerce_builder.shared.components;

import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.ManagedClassNameFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Collection;
@Getter
public class EntityManagerFactoryFactory{
    private final DataSourceHolder dataSourceHolder;
    public EntityManagerFactoryFactory(DataSourceHolder dataSourceHolder) {
        this.dataSourceHolder =dataSourceHolder;
    }
    public EntityManagerFactory create(int editorId, Collection<String> allClassNames) {
        var factoryFactory = new LocalContainerEntityManagerFactoryBean();
        factoryFactory.setDataSource(dataSourceHolder.get(editorId));
        factoryFactory.setPackagesToScan("me.yusuf.ecommerce_builder.demo.domain.dynamic.entity.editor" + editorId,
                "me.yusuf.ecommerce_builder.shared.types.entity");
        factoryFactory.setManagedClassNameFilter(new ClassNameFilter(
                '^' + String.join("|", allClassNames) + '$'
        ));
        factoryFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryFactory.setPersistenceUnitName("editor" + editorId);
        factoryFactory.afterPropertiesSet();
        return factoryFactory.getObject();
    }

    private static class ClassNameFilter implements ManagedClassNameFilter {
        private final String classNameRegex;

        public ClassNameFilter(String regex) {
            this.classNameRegex = regex;
        }

        @Override
        public boolean matches(String className) {
            return className.matches(classNameRegex);
        }
    }
}
