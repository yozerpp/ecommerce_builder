package me.yusuf.ecommerce.domain;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import me.yusuf.ecommerce.domain.session.Session;
import me.yusuf.ecommerce.domain.session.SessionHolder;
import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;

public abstract class ServiceBase {
    protected EntityManager _entityManager;
    @Value("${app.url}")
    protected String appUrl;
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
