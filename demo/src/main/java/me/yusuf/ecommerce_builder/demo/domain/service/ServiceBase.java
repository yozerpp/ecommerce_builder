package me.yusuf.ecommerce_builder.demo.domain.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.yusuf.ecommerce_builder.shared.types.entity.Session;
import me.yusuf.ecommerce_builder.demo.domain.network.filter.SessionHolder;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ServiceBase {
    @PersistenceContext(unitName = "demoEntityManagerFactory")
    protected EntityManager _entityManager;
    public static @Nullable User getUser(){
        var p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(p instanceof User u)return u;
        return null;
    }
    public static Session getSession(){
        var user = getUser();
        if(user != null) return user.getActiveSessionRef();
        return SessionHolder.getSession();
    }
}
