package me.yusuf.ecommerce_builder.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.yusuf.ecommerce_builder.demo.domain.category.CategoryRepository;
import me.yusuf.ecommerce_builder.demo.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PageInterceptor implements HandlerInterceptor {
    private final CategoryRepository categoryRepository;
    public PageInterceptor( CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || response.getStatus()>300) return;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User && !modelAndView.getModelMap().containsAttribute("notifications"))
            request.getRequestDispatcher("/notification?read=false").include(request, response);
        if (!modelAndView.getModelMap().containsAttribute("categories"))
            modelAndView.getModelMap().addAttribute("categories",categoryRepository.findBaseCategoriesAsTree(null));
    }
}