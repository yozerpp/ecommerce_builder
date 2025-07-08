package me.yusuf.ecommerce_builder.demo.engine.repository;

import me.yusuf.ecommerce_builder.demo.engine.InMemoryClassLoader;
import me.yusuf.ecommerce_builder.shared.components.DataSourceHolder;
import me.yusuf.ecommerce_builder.shared.types.entity.Cart;
import me.yusuf.utils.ReflectionUtils;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.SchemaToolingSettings;
import org.hibernate.tool.schema.Action;
import org.hibernate.tool.schema.TargetType;
import org.hibernate.tool.schema.spi.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.URL;
import java.util.*;

@Component
@Scope("singleton")
public class SchemaManager {
    private static final Map<Integer, SchemaMigrator> instances = new HashMap<>();
    private final DataSourceHolder datasourceHolder;
    private final InMemoryClassLoader classLoader;
    private final EntityManagerFactory entityManagerFactory;
    private SchemaManager(DataSourceHolder datasourceHolder, InMemoryClassLoader classLoader, EntityManagerFactory entityManagerFactory) {
        this.datasourceHolder = datasourceHolder;
        this.classLoader = classLoader;
        this.entityManagerFactory = entityManagerFactory;
    }
    public void update(Collection<? extends Class<?>> allClassNames, int editorId){
        var serviceRegistry = getServiceRegistry(datasourceHolder.get(editorId),editorId,classLoader);
//        var serviceRegistry = getServiceRegistry(datasourceHolder.get(editorId),editorId, classLoader);
        var metadata = getMetadata(serviceRegistry,classLoader,allClassNames,editorId);
        SchemaMigrator migrator;
        if ((migrator = instances.get(editorId)) == null){
            migrator = serviceRegistry.getService(SchemaManagementTool.class).getSchemaMigrator(Map.of(
                    SchemaToolingSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.ACTION_UPDATE
            ));
            instances.put(editorId, migrator);
        }
        migrator.doMigration(metadata, new ExecutionOptions() {
                @Override
                public Map<String, Object> getConfigurationValues() {
                    return Map.of();
                }
                @Override
                public boolean shouldManageNamespaces() {
                    return true;
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
    }
    private static StandardServiceRegistry getServiceRegistry(DataSource dataSource,int editorId, ClassLoader classLoader) {
        return new StandardServiceRegistryBuilder()
                .applySetting(AvailableSettings.HBM2DDL_AUTO, Action.ACTION_UPDATE)
                .applySetting(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.ACTION_UPDATE)
                .applySetting(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting(AvailableSettings.DATASOURCE, dataSource)
                .applySetting(AvailableSettings.CLASSLOADERS ,List.of(classLoader))
                .applySetting(AvailableSettings.DEFAULT_SCHEMA, "demo" + editorId)
                .addService(ClassLoaderService.class,new ClassLoaderServiceImpl(classLoader))
                .build();
    }
    private static Metadata getMetadata(StandardServiceRegistry serviceRegistry,ClassLoader dynamicClassLoader, Collection<? extends Class<?>> dynamicClasses, int editorId){
        return new MetadataSources(serviceRegistry).addAnnotatedClasses(dynamicClasses.toArray(Class[]::new))
                .getMetadataBuilder(serviceRegistry)
                .applyTempClassLoader(dynamicClassLoader)
                .applyScanEnvironment(new ScanEnvironment(){
                    @Override
                    public URL getRootUrl() {
                        return null;
                    }

                    @Override
                    public List<URL> getNonRootUrls() {
                        return List.of(ReflectionUtils.getPackageURL(Cart.class));
                    }

                    @Override
                    public List<String> getExplicitlyListedClassNames() {
                        return dynamicClasses.stream().map(Class::getName).toList();
                    }

                    @Override
                    public List<String> getExplicitlyListedMappingFiles() {
                        return List.of();
                    }
                })
                .applyImplicitSchemaName("demo" + editorId).applyScanOptions(new ScanOptions() {
                    @Override
                    public boolean canDetectUnlistedClassesInRoot() {
                        return false;
                    }

                    @Override
                    public boolean canDetectUnlistedClassesInNonRoot() {
                        return false;
                    }

                    @Override
                    public boolean canDetectHibernateMappingFiles() {
                        return false;
                    }
                }).build();
    }
}
