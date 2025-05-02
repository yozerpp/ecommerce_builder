package me.yusuf.ecommerce_builder.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"me.yusuf.ecommerce_builder.shared.components.repository"},
entityManagerFactoryRef = "editorEntityManagerFactory",transactionManagerRef = "editorTransactionManager")
public class EditorDatasourceConfig {
    @Value("${spring.datasource.editor.url}")
    private String  url;
    @Value("${spring.datasource.editor.username}")
    private String username;
    @Value("${spring.datasource.editor.password}")
    private String password;
    @Value("${spring.datasource.editor.driver-class-name}")
    private String driverClassName;
    @Bean("editorDataSource")
    DataSource editorDataSource() {
        return org.springframework.boot.jdbc.DataSourceBuilder.create()
                .driverClassName(driverClassName).password(password).url(url).username(username)
                .build();
    }
    @Bean(name = "editorEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            @Qualifier("editorDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("me.yusuf.ecommerce_builder.shared.types.plugin"); // Package for secondary entities
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // Optional: Add more JPA properties if needed
        return em;
    }

    @Bean(name = "editorTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("editorEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
