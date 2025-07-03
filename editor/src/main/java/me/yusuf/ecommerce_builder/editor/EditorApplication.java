package me.yusuf.ecommerce_builder.editor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import me.yusuf.ecommerce_builder.editor.domain.entity.Metamodel;
import me.yusuf.ecommerce_builder.editor.domain.repository.EditorRepository;
import me.yusuf.ecommerce_builder.editor.network.EditorContextHolder;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.SubstitutionVisitor;
import me.yusuf.ecommerce_builder.shared.components.EditorIdContextHolder;
import me.yusuf.ecommerce_builder.shared.types.conversion.ClassMapper;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ASTBuilderVisitor;

import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import me.yusuf.ecommerce_builder.shared.utils.SharedUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"me.yusuf.ecommerce_builder.shared.components.repository","me.yusuf.ecommerce_builder.editor"})
@EntityScan(basePackages = {"me.yusuf.ecommerce_builder.shared.types.plugin", "me.yusuf.ecommerce_builder.editor.domain.entity"})
@EnableJpaRepositories(basePackages = {"me.yusuf.ecommerce_builder.shared.components.repository","me.yusuf.ecommerce_builder.editor.domain.repository"})
public class EditorApplication {
    @Getter
    private static final boolean KUBE_DEPLOYMENT = System.getenv("KUBERNETES_SERVICE_HOST")!=null;
    @Getter
    private static final boolean TEST = System.getProperty("isTest")!=null;
    public static void main(String[] args){
        SpringApplication.run(EditorApplication.class, args);
    }
    @Value("${spring.datasource.demo.url}")
    private String demoDatasourceUrl;
    @Bean
    @Scope("singleton")
    ObjectMapper objectMapper(){
        return new ClassMapper();
    }
    @Bean
    @Scope("singleton")
    public FilterRegistrationBean<EditorIdContextHolder> editorContextHolderFilter(){
        var filter = new FilterRegistrationBean<>(new EditorIdContextHolder());
        filter.addUrlPatterns("/api/demo/*");
        filter.setOrder(1);
        filter.setAsyncSupported(false);
        return filter;
    }
    @Bean
    @Scope
    public Map<Class<?>, Metamodel> getDefaultMetamodels() throws IOException {
        return Metamodel.generate(Arrays.stream(defaultEntityClasses()).filter(c->c.getDeclaringClass()==null).toList(),0);
    }
    @Bean
    @Scope("singleton")
    public FilterRegistrationBean<EditorContextHolder> editorContextHodlerFilter(EditorRepository editorRepository){
        var filter = new FilterRegistrationBean<>(new EditorContextHolder(editorRepository));
        filter.addUrlPatterns("/api/demo/*");
        filter.setOrder(2);
        filter.setAsyncSupported(false);
        return filter;
    }

//    @Bean
//    @Scope("singleton")
//    Map<Class<?>, Metamodel> defaultMetamodels(Class<?>[] defaultEntityClasses) {
//        return Metamodel.generate(Arrays.asList(defaultEntityClasses),0);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login", "/public/**", "/sign-up","/static/**", "/static/css/styles.css", "/css/**","/image/**").permitAll() // Allow public access to login
                                .anyRequest().permitAll() // Protect other endpoints
                );
        return http.build();
    }
    @Bean
    @Scope("singleton")
    public Class<?>[] defaultEntityClasses() throws IOException {
        return SharedUtils.getPatternMatchingClasses("classpath*:me.yusuf.ecommerce_builder.shared.types.entity.*.class");
    }
    @Bean
    @Scope("singleton")
    ASTBuilderVisitor astBuilderVisitor(){
        return new ASTBuilderVisitor();
    }
    @Bean
    @Scope("singleton")
    SubstitutionVisitor.Factory substitutionVisitorFactory() {
        return new SubstitutionVisitor.Factory( Map.ofEntries(
                Map.entry(new Tuple2<>("SEPET", SubstitutionVisitor.SymbolType.Variable), "Cart"),
                Map.entry(new Tuple2<>("KULLANICI", SubstitutionVisitor.SymbolType.Variable), "User"),
                Map.entry(new Tuple2<>("SATICI", SubstitutionVisitor.SymbolType.Variable), "Seller"),
                Map.entry(new Tuple2<>("İLAN", SubstitutionVisitor.SymbolType.Variable), "ProductOffer"),
                Map.entry(new Tuple2<>("SEPETÜRÜNÜ", SubstitutionVisitor.SymbolType.Variable), "CartItem"),
                Map.entry(new Tuple2<>("ÜRÜN", SubstitutionVisitor.SymbolType.Variable), "Product"),
                Map.entry(new Tuple2<>("KATEGORİ", SubstitutionVisitor.SymbolType.Variable), "Category"),
                Map.entry(new Tuple2<>("ÖDEME", SubstitutionVisitor.SymbolType.Variable), "Payment"),
                Map.entry(new Tuple2<>("ÖDEMELER", SubstitutionVisitor.SymbolType.Variable), "Payments"),
                Map.entry(new Tuple2<>("SİPARİŞ", SubstitutionVisitor.SymbolType.Variable), "Order"),
                Map.entry(new Tuple2<>("yazdır", SubstitutionVisitor.SymbolType.Function), "System.out.println")
        ));
    }
}
