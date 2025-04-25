package me.yusuf.ecommerce_builder.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import me.yusuf.ecommerce_builder.demo.controller.PageInterceptor;
import me.yusuf.ecommerce_builder.demo.security.UserAuthService;
import me.yusuf.ecommerce_builder.shared.components.EditorContextHolder;
import me.yusuf.ecommerce_builder.demo.engine.MethodMetadataRegistry;
import me.yusuf.ecommerce_builder.shared.types.conversion.ClassMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.IOException;
import java.util.Objects;

@OpenAPIDefinition(info = @Info(title = "ecommerce", version = "1.0"))
@EnableWebMvc
@EnableMethodSecurity
@EnableJpaRepositories(basePackages = {"me.yusuf.ecommerce_builder.demo.domain","me.yusuf.ecommerce_builder.demo.engine"})
@SpringBootApplication(scanBasePackages = {"me.yusuf.ecommerce_builder.demo", "me.yusuf.ecommerce_builder.demo.controller", "me.yusuf.ecommerce_builder.demo.engine"})
public class EcommerceApplication implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer{
    public static final boolean KUBE_DEPLOYMENT = System.getenv("KUBERNETES_SERVICE_HOST")!=null;
    public EcommerceApplication(PageInterceptor pageInterceptor, UserAuthService userAuthService) {
        this.pageInterceptor = pageInterceptor;
        this.userAuthService = userAuthService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
    final PageInterceptor pageInterceptor;
    @Bean
    @Scope("singleton")
    public MethodMetadataRegistry methodMetadataRegistry() {
        return new MethodMetadataRegistry("me.yusuf.ecommerce.service");
    }
    @Bean(name = "kubernetesClient")
    @Scope("singleton")
    public ApiClient kubernetesClient() throws IOException {
        if (!KUBE_DEPLOYMENT)
            return new ApiClient();
        else return Config.defaultClient();
    }
    @Bean
    FilterRegistrationBean<EditorContextHolder> editorContextHolder(){
        var ret = new FilterRegistrationBean<>(new EditorContextHolder());
        ret.setOrder(1);
        ret.addUrlPatterns("/engine/*");
        ret.setAsyncSupported(true);
        return ret;
    }
    @Bean
    @Scope("singleton")
    ObjectMapper objectMapper(){
        return new ClassMapper();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageInterceptor).addPathPatterns("/**").excludePathPatterns("/img/**","/css/**","/css/style.css","/static/**","/*/api/**","/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
    }

    public final UserAuthService userAuthService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login", "/public/**", "/sign-up","/static/**", "/static/css/styles.css", "/css/**","/image/**").permitAll() // Allow public access to login
                        .anyRequest().permitAll() // Protect other endpoints
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
