package me.yusuf.ecommerce_builder.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.SynchronizationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {
    @PersistenceContext(unitName = "demoEntityManagerFactory")
    EntityManager entityManager;
    private final EcommerceDatabaseInitializer ecommerceDatabaseInitializer;
    @Autowired
    public DemoApplicationTests(EcommerceDatabaseInitializer ecommerceDatabaseInitializer) {
        this.ecommerceDatabaseInitializer = ecommerceDatabaseInitializer;
    }
    @BeforeAll
    static void init() {
        System.setProperty("test.env", "true");
    }
    @Test
    @Transactional(transactionManager = "demoTransactionManager")
    void initializeDatabase() {
        var q= entityManager.createNativeQuery("SELECT i.table_name FROM ecommerce.information_schema.tables i where i.table_schema='public'");
        List<String> tables = q.getResultStream().map(Object::toString).toList();
        if(tables.stream().filter(t->!(t.equals("notification") || t.equals("card_payment")))
                .anyMatch(t-> entityManager.createNativeQuery("SELECT COUNT(*) FROM ecommerce.public." + t).getResultList().get(0).equals(0L))){
            tables.forEach(t-> entityManager.createNativeQuery("TRUNCATE TABLE ecommerce.public."+t + " CASCADE"));
            ecommerceDatabaseInitializer.initializeDatabase();
        }
    }

}
