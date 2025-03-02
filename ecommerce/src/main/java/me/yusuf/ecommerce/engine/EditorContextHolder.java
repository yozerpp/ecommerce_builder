package me.yusuf.ecommerce.engine;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

//@ConditionalOnProperty(prefix = "deployment", name = "type", havingValue = "editor")
@WebFilter(urlPatterns = "/engine/*")
@Component
public class EditorContextHolder extends HttpFilter {

    private static final ThreadLocal<Integer> holder = new ThreadLocal<>();
    public static Integer getUserId(){
        return holder.get();
    }
    private static void setEditorId(Integer userId){
        holder.set(userId);
    }
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
       Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("editorId")).findFirst().ifPresent(cookie -> {
//           holder.set(Integer.parseInt(cookie.getValue())); //TODO: uncomment
       });
        holder.set(1);
       chain.doFilter(request,response);
    }
}
