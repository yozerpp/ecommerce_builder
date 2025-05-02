package me.yusuf.ecommerce_builder.shared.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

public class EditorContextHolder extends HttpFilter {
    private static final ThreadLocal<Integer> editorId = new ThreadLocal<>();
    private static final boolean DEBUG_MODE = true;
    public EditorContextHolder(){
        super();
    }
    public static Integer getEditorId(){
        if (DEBUG_MODE) return 0;
        else return editorId.get();
    }
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Arrays.stream(req.getCookies()).filter(c->c.getName().equals("EditorId")).findFirst().ifPresent(c->editorId.set(Integer.parseInt(c.getValue())));
        chain.doFilter(req,res);
    }
}
