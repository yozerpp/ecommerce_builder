package me.yusuf.ecommerce.domain;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ServiceBase {
    protected EntityManager _entityManager;
    @Value("${app.url}")
    protected String appUrl;
    public ServiceBase(EntityManager entityManager) {
        this._entityManager = entityManager;
    }
    public static @Nullable User getUser(){
        var p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return p!=null? (User) p : null;
    }
}
