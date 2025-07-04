package me.yusuf.ecommerce_builder.demo.engine.repository;

import me.yusuf.ecommerce_builder.demo.engine.InMemoryClassLoader;
import me.yusuf.ecommerce_builder.shared.components.DataSourceHolder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.spi.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

@Component
@Scope("singleton")
public class SchemaManager {
    private static final Map<Integer, SchemaMigrator> instances = new HashMap<>();
    private final DataSourceHolder datasourceHolder;
    private final InMemoryClassLoader classLoader;
    private SchemaManager(DataSourceHolder datasourceHolder, InMemoryClassLoader classLoader) {
        this.datasourceHolder = datasourceHolder;
        this.classLoader = classLoader;
    }
    public void update(Collection<? extends Class<?>> allClassNames, int editorId){
        var orgClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
        var serviceRegistry = getServiceRegistry(datasourceHolder.get(editorId), classLoader);
        var metadata = getMetadata(serviceRegistry,allClassNames);
        SchemaMigrator migrator;
        if ((migrator = instances.get(editorId)) == null){
            migrator = serviceRegistry.getService(SchemaManagementTool.class).getSchemaMigrator();
            instances.put(editorId, migrator);
        }
        migrator.doMigration(metadata, new ExecutionOptions() {
                    @Override
                    public Map<String, Object> getConfigurationValues() {
                        return Map.of();
                    }
                    @Override
                    public boolean shouldManageNamespaces() {
                        return false;
                    }

                    @Override
                    public ExceptionHandler getExceptionHandler() {
                        return e -> {
                            System.out.println(e.getMessage());
                            e.printStackTrace(System.out);
                        };
                    }
                }, ContributableMatcher.ALL,
                new TargetDescriptor() {
                    @Override
                    public EnumSet<TargetType> getTargetTypes() {
                        return EnumSet.of(TargetType.DATABASE);
                    }

                    @Override
                    public ScriptTargetOutput getScriptTargetOutput() {
                        return null;
                    }
                });
        } finally {
            Thread.currentThread().setContextClassLoader(orgClassLoader);
        }

    }
    private static StandardServiceRegistry getServiceRegistry(DataSource dataSource, ClassLoader classLoader) {
        return new StandardServiceRegistryBuilder()
                .applySetting("hibernate.hbm2ddl.auto", "none")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting("hibernate.connection.datasource", dataSource)
                .applySetting("hibernate.classLoaders" ,classLoader)
                .addService(ClassLoaderService.class,new ClassLoaderServiceImpl(classLoader))
                .build();
    }
    private static Metadata getMetadata(StandardServiceRegistry serviceRegistry, Collection<? extends Class<?>> allClassNames){
    return new MetadataSources(serviceRegistry).addAnnotatedClasses(allClassNames.toArray(Class[]::new)).buildMetadata();
    }
}
