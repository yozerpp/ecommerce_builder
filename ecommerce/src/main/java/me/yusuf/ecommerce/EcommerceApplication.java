package me.yusuf.ecommerce;

import me.yusuf.ecommerce.controller.PageInterceptor;
import me.yusuf.ecommerce.security.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;
@EnableWebMvc
@EnableMethodSecurity
@EnableJpaRepositories(basePackages = "me.yusuf.ecommerce.domain")
@SpringBootApplication(scanBasePackages = {"me.yusuf.ecommerce", "me.yusuf.ecommerce.controller"})
public class EcommerceApplication implements WebMvcConfigurer{
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
    @Autowired PageInterceptor pageInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageInterceptor).addPathPatterns("/**").excludePathPatterns("/img/**","/css/**","/css/style.css","/static/**","/*/api/**","/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    }

    @Autowired
     public UserAuthService userAuthService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/public/**", "/sign-up","/static/**", "/static/css/styles.css", "/css/**","/image/**").permitAll() // Allow public access to login
                        .anyRequest().authenticated() // Protect other endpoints
                ).userDetailsService(userAuthService)
                .formLogin(form -> form
                        .loginPage("/login") // Point to your custom login page
                        .permitAll() // Allow everyone to access it
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public DefaultCookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("JSESSIONID"); // Default cookie label
        cookieSerializer.setCookiePath("/");         // Cookie path
        cookieSerializer.setUseHttpOnlyCookie(true); // For security
        cookieSerializer.setUseSecureCookie(true);   // Use HTTPS (recommended)
        cookieSerializer.setCookieMaxAge(86400 * 30);          // 1 day in seconds
        return cookieSerializer;
    }
}
