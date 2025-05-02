package me.yusuf.ecommerce_builder.editor.network;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.yusuf.ecommerce_builder.editor.domain.entity.Editor;
import me.yusuf.ecommerce_builder.editor.domain.repository.EditorRepository;
import me.yusuf.ecommerce_builder.shared.components.EditorIdContextHolder;

import java.io.IOException;

public class EditorContextHolder extends HttpFilter {
    private static final ThreadLocal<Editor> editorThreadLocal = new ThreadLocal<>();
    private final EditorRepository editorRepository;
    public EditorContextHolder(EditorRepository editorRepository) {
        this.editorRepository = editorRepository;
    }
    public static Editor getEditor() {
        return editorThreadLocal.get();
    }
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Editor editor;
        if((editor= (Editor) req.getSession().getAttribute("editor")) ==null) {
            editor=editorRepository.findById(EditorIdContextHolder.getEditorId());
            req.getSession().setAttribute("editor", editor);
        }
        editorThreadLocal.set(editor);
        try{
            chain.doFilter(req, res);
        } finally {
            editorThreadLocal.remove();
        }
    }
}
