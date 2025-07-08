package me.yusuf.ecommerce_builder.demo.domain.network.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.yusuf.ecommerce_builder.shared.types.entity.Cart;
import me.yusuf.ecommerce_builder.demo.domain.repository.CartRepository;
import me.yusuf.ecommerce_builder.shared.types.entity.Session;
import me.yusuf.ecommerce_builder.demo.domain.repository.SessionRepository;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class SessionHolder extends HttpFilter {
    private static final ThreadLocal<Session> sessionHolder = new ThreadLocal<>();
    private final SessionRepository sessionRepository;
    private final CartRepository cartRepository;
    public static Session getSession(){
        return sessionHolder.get();
    }
    public SessionHolder(SessionRepository sessionRepository, CartRepository cartRepository) {
        this.sessionRepository = sessionRepository;
        this.cartRepository = cartRepository;
    }
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof User user) {
            sessionHolder.set(user.getActiveSessionRef());
        } else {
            var t = request.getRequestedSessionId();
            var sid = t != null ? t : request.getSession().getId();
            var sopt = sessionRepository.findById(sid);
            var session = sopt.orElseGet(() -> {
                var cart = new Cart();
                cart = cartRepository.save(cart);
                var s = new Session();
                s.setId(sid);
                s.setCart(cart);
                s.setCartId(cart.getId());
                s = sessionRepository.save(s);
                return s;
            });
            sessionHolder.set(session);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            sessionHolder.remove();
        }
    }
}
