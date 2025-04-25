package me.yusuf.ecommerce_builder.editor;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.yusuf.ecommerce_builder.shared.components.EditorContextHolder;
import me.yusuf.ecommerce_builder.shared.types.conversion.ClassMapper;
import me.yusuf.ecommerce_builder.editor.transpiler.ASTBuilderVisitor;
import me.yusuf.ecommerce_builder.editor.transpiler.CodeGeneratorVisitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class EcommerceBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBuilderApplication.class, args);
    }
    @Bean
    @Scope("singleton")
    ObjectMapper objectMapper(){
        return new ClassMapper();
    }
    @Bean
    @Scope("singleton")
    FilterRegistrationBean<EditorContextHolder> editorContextHolderFilter(){
        var filter = new FilterRegistrationBean<EditorContextHolder>(new EditorContextHolder());
        filter.addUrlPatterns("/ecommerce/engine/*");
        filter.setOrder(1);
        return filter;
    }
    @Bean
    @Scope("singleton")
    ASTBuilderVisitor astBuilderVisitor(){
        return new ASTBuilderVisitor();
    }
    @Bean
    @Scope("singleton")
    CodeGeneratorVisitor codeGeneratorVisitor(){
        return new CodeGeneratorVisitor();
    }
//    @Bean(name = "methodMetadataRegistry")
//    @Scope("singleton")
//    MethodMetadataRegistry methodMetadataRegistry(CodeGeneratorService ser) throws IOException {
//        HttpURLConnection.setFollowRedirects(true);
//        try (var http = HttpClient.newHttpClient()) {
//            var res = http.send(HttpRequest.newBuilder(URI.create("http://ecommerce/ecommerce/engine/metadata"))
//                    .GET().build(), HttpResponse.BodyHandlers.ofInputStream()).body();
//            Tuple2<Class<?>[],Map<Method, MethodMetadata>> map = objectMapper().readerFor(new TypeReference<Tuple2<Class<?>[],Map<Method, MethodMetadata>>>() {
//            }).readValue(res);
//            return new MethodMetadataRegistry(map._2(), map._1());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Bean
//    @Scope("singleton")
//    @DependsOn("methodMetadataRegistry")
//    Completer completer(MethodMetadataRegistry registry){
//        return new Completer(registry);
//    }
}
