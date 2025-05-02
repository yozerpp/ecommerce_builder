package me.yusuf.ecommerce_builder.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@jakarta.transaction.Transactional
@Transactional(transactionManager = "demoTransactionManager")
public class DummyManager {
    @PersistenceContext(unitName = "demoEntityManagerFactory")
    @Autowired
            @Getter
    EntityManager entityManager;
    public DummyManager(){
    }
    void persist(Object o) {
        entityManager.persist(o);
    }
}
