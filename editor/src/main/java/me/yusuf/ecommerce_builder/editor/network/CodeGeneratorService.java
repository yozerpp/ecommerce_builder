package me.yusuf.ecommerce_builder.editor.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.yusuf.ecommerce_builder.editor.compiler.DynamicCompiler;
import me.yusuf.ecommerce_builder.editor.helper.Utils;
import me.yusuf.ecommerce_builder.shared.components.EditorContextHolder;
import me.yusuf.ecommerce_builder.shared.types.ClassFileObject;
import me.yusuf.ecommerce_builder.shared.types.MethodMetadata;
import me.yusuf.ecommerce_builder.shared.types.PluginSourceAndMetadata;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import me.yusuf.ecommerce_builder.editor.transpiler.ASTBuilderVisitor;
import me.yusuf.ecommerce_builder.editor.transpiler.CodeGeneratorVisitor;
import me.yusuf.ecommerce_builder.editor.transpiler.Completer;
import me.yusuf.ecommerce_builder.editor.transpiler.generated.TurkishPseudoCodeParser;
import me.yusuf.utils.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CodeGeneratorService {
    private static final Map<Integer, String> tokenMaps = getTokenMaps();
    private static final Map<Integer, String> ruleMaps = getRuleMaps();
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final String demoUrl = "https://demo-service-internal:5001";
    private final Completer completer;
    private final CodeGeneratorVisitor generator;
    private final ASTBuilderVisitor astBuilder;
    private final ObjectMapper objectMapper;

    public CodeGeneratorService(final Completer completer, final CodeGeneratorVisitor generator, final ASTBuilderVisitor astBuilder, ObjectMapper objectMapper) {
        this.completer = completer;
        this.astBuilder = astBuilder;
        this.generator = generator;
        this.objectMapper = objectMapper;
    }
    public ClassFileObject generate(String input)  {
        var plugin = parse(input.trim());
        return DynamicCompiler.compile(plugin.id().name(),
                plugin.source());
    }
    public void sendToApp(ClassFileObject classFileObject) throws BadRequestException {
        HttpResponse<String> res;
        try {
            res =  httpClient.send(HttpRequest.newBuilder(URI.create(demoUrl + "/engine/plugin"))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(classFileObject))).build()
                    , HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        if (res.statusCode() != 200) {
            throw new BadRequestException(res.body());
        }
    }
public Map<String, Tuple2<String, MethodMetadata>> getMetadatas(){
        HttpResponse<String> res;
        try {
            res = httpClient.send(HttpRequest.newBuilder(URI.create(demoUrl)).GET().build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return objectMapper.readerFor(new TypeReference<Map<String, Tuple2<String, MethodMetadata>>>() {
            }).readValue(res.body());
        }  catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
}
    private PluginSourceAndMetadata parse(String input){
        var par= Utils.getParser(input);
        var işlevTanımı = astBuilder.visitIşlevTanımı(par.işlevTanımı());
        return generator.generate(işlevTanımı,EditorContextHolder.getEditorId());
    }
//    public void completions(String s){
//        var parser = Utils.getParser(s);
//        var ctx  =parser.işlevTanımı();
//        var engine = new CodeCompletionCore(parser,null,null);
//        var candidates =  engine.collectCandidates(0,parser.getContext());
//        candidates.rules.entrySet().stream().map(e->{
//
//        })
//    }
//    private static void getPossibleReferences(final Integer i){
//        if (i == TurkishPseudoCodeParser.RULE_değişkenTanımı)
//    }
    private static Map<Integer, String> getTokenMaps() {
        return Arrays.stream(TurkishPseudoCodeParser.class.getDeclaredFields()).filter(f -> int.class.isAssignableFrom(f.getClass()))
                .filter(f -> !f.getName().matches("^RULE_\\w+")).collect(Collectors.toMap(f -> {
                    try {
                        return (Integer) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }, f -> StringUtils.splitWhole(f.getName()).toLowerCase(Locale.forLanguageTag("TR"))));
    }
    private static Map<Integer, String> getRuleMaps(){
        return Arrays.stream(TurkishPseudoCodeParser.class.getDeclaredFields()).filter(f->int.class.isAssignableFrom(f.getClass()))
                .filter(f->f.getName().matches("^RULE_\\w+")).collect(Collectors.toMap(f-> {
                    try {
                        return (Integer) f.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }, f-> StringUtils.splitWhole(f.getName().replace("RULE_","")).toLowerCase(Locale.forLanguageTag("TR"))));
    }
}