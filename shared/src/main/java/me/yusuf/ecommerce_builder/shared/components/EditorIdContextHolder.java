package me.yusuf.ecommerce_builder.shared.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

public class EditorIdContextHolder extends HttpFilter {
    private static final ThreadLocal<Integer> editorId = new ThreadLocal<>();
    public EditorIdContextHolder(){
        super();
    }
    public static Integer getEditorId(){
        return editorId.get();
    }
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Integer id;
        if((id = (Integer) req.getSession().getAttribute("editorId"))==null){
           id=Arrays.stream(req.getCookies()).filter(c-> c.getName().equals("editorId")).findAny().map(c->Integer.parseInt(c.getValue())).orElse(null);
            req.getSession().setAttribute("editorId", id);
        }
        editorId.set(id);
        try {
            chain.doFilter(req, res);
        }finally {
            editorId.remove();
        }
    }
}
