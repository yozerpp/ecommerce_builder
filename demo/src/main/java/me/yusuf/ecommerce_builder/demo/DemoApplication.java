package me.yusuf.ecommerce_builder.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.Config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import me.yusuf.ecommerce_builder.demo.domain.network.filter.SessionHolder;
import me.yusuf.ecommerce_builder.demo.domain.repository.CartRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.SessionRepository;
import me.yusuf.ecommerce_builder.shared.components.DataSourceHolder;
import me.yusuf.ecommerce_builder.demo.domain.network.PageInterceptor;
import me.yusuf.ecommerce_builder.demo.security.UserAuthService;
import me.yusuf.ecommerce_builder.shared.components.EditorIdContextHolder;
import me.yusuf.ecommerce_builder.shared.components.EntityManagerFactoryFactory;
import me.yusuf.ecommerce_builder.shared.components.MethodMetadataRegistry;
import me.yusuf.ecommerce_builder.shared.types.conversion.ClassMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@OpenAPIDefinition(info = @Info(title = "ecommerce", version = "1.0"))
@EnableWebMvc
@EnableMethodSecurity
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"me.yusuf.ecommerce_builder.demo", "me.yusuf.ecommerce_builder.demo.domain.network", "me.yusuf.ecommerce_builder.demo.engine","me.yusuf.ecommerce_builder.shared.components.repository"})
public class DemoApplication implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
    private final DemoDatasourceConfig demoDatasourceConfig;
    public static final boolean KUBE_DEPLOYMENT = System.getenv("KUBERNETES_SERVICE_HOST")!=null;
    public DemoApplication(@Qualifier("demoDataSource") DataSource defaultDatasource, DemoDatasourceConfig demoDatasourceConfig, PageInterceptor pageInterceptor, UserAuthService userAuthService) {
        this.defaultDatasource = defaultDatasource;
        this.pageInterceptor = pageInterceptor;
        this.userAuthService = userAuthService;
        this.demoDatasourceConfig = demoDatasourceConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    @Scope("singleton")
    public MethodMetadataRegistry methodMetadataRegistry() {
        return new MethodMetadataRegistry("me.yusuf.ecommerce_builder.demo.domain.service");
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.replaceAll(c->{
            if (c instanceof MappingJackson2HttpMessageConverter) {
                return new MappingJackson2HttpMessageConverter(objectMapper());
            } else return c;
        });
        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
    @Bean(name = "kubernetesClient")
    @Scope("singleton")
    public ApiClient kubernetesClient() throws IOException {
        if (!KUBE_DEPLOYMENT)
            return new ApiClient();
        else return Config.fromCluster();
    }
    @Bean
    FilterRegistrationBean<EditorIdContextHolder> editorIdContextHolderFilterRegistrationBean(){
        var ret = new FilterRegistrationBean<>(new EditorIdContextHolder());
        ret.setOrder(1);
        ret.addUrlPatterns("/api/*", "/api/*/*", "/api/*/*/*", "/api/*/*/*/*");
        ret.setAsyncSupported(false);
        return ret;
    }

    @Bean
    FilterRegistrationBean<SessionHolder> sessionHolderFilterRegistrationBean(SessionRepository sessionRepository, CartRepository cartRepository){
        var ret = new FilterRegistrationBean<>(new SessionHolder(sessionRepository, cartRepository));
        ret.setOrder(3);
        ret.addUrlPatterns("/api/*", "/api/*/*", "/api/*/*/*", "/api/*/*/*/*");
        ret.setAsyncSupported(false);
        return ret;
    }
    @Bean
    @Scope("singleton")
    DataSourceHolder dataSourceHolder(){
        return new DataSourceHolder(defaultDatasource, demoDatasourceConfig.getUrl());
    }
    @Bean
    @Scope("singleton")
    EntityManagerFactoryFactory entityManagerFactoryFactory(){
        return new EntityManagerFactoryFactory(dataSourceHolder());
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
    @Qualifier("demoDataSource")
    private final DataSource defaultDatasource;
    private final PageInterceptor pageInterceptor;
    private final UserAuthService userAuthService;
}
