package me.yusuf.ecommerce_builder.demo.domain.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.yusuf.ecommerce_builder.demo.security.UserPrincipal;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import me.yusuf.ecommerce_builder.shared.types.entity.Cart;
import me.yusuf.ecommerce_builder.shared.types.entity.Session;
import me.yusuf.ecommerce_builder.demo.domain.network.filter.SessionHolder;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ServiceBase {
    @PersistenceContext(unitName = "demoEntityManagerFactory")
    protected EntityManager _entityManager;
    public static @Nullable User getUser(){
        var p = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (p instanceof User u) return u;
        else return null;
    }
    public static Session getSession(){
        var user = getUser();
        if(user != null) return user.getActiveSessionRef();
        return SessionHolder.getSession();
    }

    @MethodInfo(userFriendlyName = "Sepet Getir")
    public static Cart getCart(){
        var session = getSession();
        var cart = session.getCart();
        if (cart == null) throw new IllegalStateException("User has no cart");
        else if (cart.isOrdered()) throw new IllegalStateException("Cart is already ordered");
        return cart;
    }
}
