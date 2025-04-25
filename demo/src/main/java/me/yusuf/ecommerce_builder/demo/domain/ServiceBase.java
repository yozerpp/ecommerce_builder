package me.yusuf.ecommerce_builder.demo.domain;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.demo.domain.session.Session;
import me.yusuf.ecommerce_builder.demo.domain.session.SessionHolder;
import me.yusuf.ecommerce_builder.demo.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ServiceBase {
    protected EntityManager _entityManager;
    public ServiceBase(EntityManager entityManager) {
        this._entityManager = entityManager;
    }
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
