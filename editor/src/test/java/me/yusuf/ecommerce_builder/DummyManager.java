package me.yusuf.ecommerce;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@jakarta.transaction.Transactional
@Transactional
public class DummyManager {
    EntityManager entityManager;
    public DummyManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    void persist(Object o) {
        entityManager.persist(o);
    }
}
