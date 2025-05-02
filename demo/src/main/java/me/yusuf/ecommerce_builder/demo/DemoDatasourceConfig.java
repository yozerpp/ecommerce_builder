package me.yusuf.ecommerce_builder.demo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "me.yusuf.ecommerce_builder.demo.domain.repository",entityManagerFactoryRef = "demoEntityManagerFactory",transactionManagerRef = "demoTransactionManager")
public class DemoDatasourceConfig {
    @Getter
    @Value("${spring.datasource.demo.url}")
    private String  url;
    @Value("${spring.datasource.demo.username}")
    private String username;
    @Value("${spring.datasource.demo.password}")
    private String password;
    @Value("${spring.datasource.demo.driver-class-name}")
    private String driverClassName;
    @Bean("demoDataSource")
    DataSource demoDataSource() {
        return DataSourceBuilder.create().url(url).username(username).password(password).driverClassName(driverClassName).build();
    }
    @Bean(name = "demoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            @Qualifier("demoDataSource") DataSource dataSource) {
        var props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "update");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("me.yusuf.ecommerce_builder.shared.types.entity"); // Package for secondary entities
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(props);
        return em;
    }

    @Bean(name = "demoTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("demoEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
