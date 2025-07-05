package me.yusuf.ecommerce_builder.editor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import me.yusuf.ecommerce_builder.editor.EditorApplication;
import me.yusuf.ecommerce_builder.editor.helper.Importer;
import me.yusuf.ecommerce_builder.shared.components.repository.EntitySourceRepository;
import me.yusuf.ecommerce_builder.shared.types.plugin.*;
import me.yusuf.ecommerce_builder.shared.components.repository.PluginRepository;
import me.yusuf.ecommerce_builder.editor.tool.compiler.DynamicCompiler;
import me.yusuf.ecommerce_builder.editor.helper.DemoClient;
import me.yusuf.ecommerce_builder.editor.helper.Utils;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ASTBuilderVisitor;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.CodeGeneratorVisitor;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.SubstitutionVisitor;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.shared.components.EditorIdContextHolder;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class CodeGeneratorService {
    private static final DemoClient demoClient = new DemoClient("engine/");
    private final CodeGeneratorVisitor generator;
    private final PluginRepository pluginRepository;
    private final Map<String, Map<String, MethodMetadata>> metadata = new HashMap<>();
    private static final String engineKey = Base64.getEncoder().encodeToString(Sha512DigestUtils.sha("Y&5v9+57bEhq"));
    private final ASTBuilderVisitor astBuilder;
    private final ObjectMapper objectMapper;
    private final SubstitutionVisitor.Factory substitutionVisitorFactory;
    private final Importer importer;
    private EntitySourceRepository entitySourceRepository;
    private final Class<?>[] defaultEntityClasses;
    public CodeGeneratorService(PluginRepository pluginRepository, Class<?>[] defaultEntityClasses, EntitySourceRepository entitySourceRepository, Importer importer, CodeGeneratorVisitor generator, ASTBuilderVisitor astBuilder
            , ObjectMapper objectMapper, SubstitutionVisitor.Factory substitutionVisitorFactory) {
        this.defaultEntityClasses = defaultEntityClasses;
        this.pluginRepository = pluginRepository;
        this.entitySourceRepository = entitySourceRepository;
        this.astBuilder = astBuilder;
        this.generator = generator;
        this.importer = importer;
        this.substitutionVisitorFactory = substitutionVisitorFactory;
        this.objectMapper = objectMapper;
    }
    @PostConstruct
    void initMetadatas() throws IOException, InterruptedException {
        if (EditorApplication.isTEST()) return;
        var res = demoClient.send("metadata", "GET", HttpRequest.BodyPublishers.noBody(), HttpResponse.BodyHandlers.ofInputStream());
        metadata.putAll( objectMapper.readerFor(new TypeReference<Map<String, Map<String, MethodMetadata>>>() {
                })
                .readValue(res.body()));
    }
    public void createPlugin(String input, int editorId) throws BadRequestException {
        var ast = parse(input);
        var argTypes = substituteAndValidate(ast);
        var javaCode = transpile(ast, editorId,argTypes);
        javaCode.getMetadata().setArgumentTypes(argTypes);
        var pluginClassFile = compile(javaCode,getImports(javaCode,editorId), Arrays.asList(entitySourceRepository.findById_EditorId(editorId, Pageable.unpaged()).toArray(EntitySource[]::new)));
        javaCode.getSource().setByteEncoded(pluginClassFile.getClassBytes());
        sendToApp(javaCode, editorId);
         javaCode.getSource().setPseudoCode(input);
        pluginRepository.save(javaCode);
    }
    public PluginDto[] recompilePlugins(int editorId, List<EntitySource> newEntityClasses){
        var userPlugins= pluginRepository.findById_EditorId(editorId, Pageable.unpaged(),PluginDto.class);
        for (var plugin:userPlugins){
            var newId = new IPlugin.Id(editorId,plugin.getId().getName(),plugin.getId().getHookedMethod(), plugin.getId().getVersion() + 1);
            plugin.setId(newId);
            var nameReplacedSource = plugin.getSource().getCharEncoded().replaceFirst("public\\s+class\\s+\\w+_\\d+_v\\d+", "public class " + newId.getName() +"Plugin"+ "_" + editorId + "_v" + newId.getVersion());
            plugin.getSource().setCharEncoded(nameReplacedSource);
            String imports = Arrays.stream(plugin.getMetadata().argTypes())
                    .map(t-> "import " + EntitySource.getClassName(t.getTypeName().replaceAll("(\\w+\\.)+",""),newEntityClasses.getFirst().getId().version(),editorId) + ";\n")
                    .reduce("", String::concat);
            var cob = compile(plugin,imports,newEntityClasses);
            plugin.getSource().setByteEncoded( cob.getClassBytes());
        }
        return userPlugins.toArray(PluginDto[]::new);
    }

    private String getImports(PluginDto source, int editorId) {
        return importer.getImports(editorId, Arrays.stream(source.getMetadata().argTypes()).map(t -> (Class<?>) t).toArray(Class[]::new));
    }

    private ClassFileObject compile(PluginDto plugin, String importBlock, List<EntitySource> entitySources){
        final var sources = new ArrayList<DynamicCompiler.SourceFile>(entitySources.size()*2 + 1);
        sources.add(new DynamicCompiler.SourceFile(plugin.getClassName(),
                "package "+ IPlugin.PLUGIN_PACKAGE_PREFIX + ";\n" +
                        importBlock +
                        plugin.getSource().getCharEncoded()
        ));
        entitySources.stream()
                .map(es-> new DynamicCompiler.SourceFile(EntitySource.getClassName(es),es.getCharEncoded()))
                .forEach(sources::add);
        Arrays.stream(defaultEntityClasses).filter(c->c.getDeclaringClass()==null)
                .map(c->new DynamicCompiler.SourceFile(c.getName(),Utils.getStaticClassSource(c)))
                .forEach(sources::add);
        var cobs = DynamicCompiler.compile(sources.toArray(DynamicCompiler.SourceFile[]::new));
        return cobs.stream()
                .filter(c->c.getClassName().replaceAll("_.*","").equals(plugin.getClassName().replaceAll("_.*","")))
                .findAny().orElseThrow();
    }

    public PluginDto transpile(final PluginDef ast, int editorId, Type[] argTypes){
        var ver = pluginRepository.getLastVersion(editorId,ast.getName(),ast.getHookedMethod());
        var splt = ast.getHookedMethod().split("\\.");
        return generator.generate(ast,argTypes,EditorIdContextHolder.getEditorId(),ver!=null?ver+1:1 );
    }

     private Type[] substituteAndValidate(PluginDef ast) throws IllegalStateException{
        var substitutionVisitor = substitutionVisitorFactory.create();
        substitutionVisitor.visitPluginDef(ast);
        return substitutionVisitor.getEntityTypes().stream().map(s-> {
            try {
                return Class.forName(EntitySource.STATIC_PACKAGE_PREFIX + '.' + s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Type[]::new);
    }

    public void sendToApp(PluginDto plugin, int editorId) throws BadRequestException {
        HttpResponse<String> res;
        try {
            res =  demoClient.send("plugin","POST",
                    HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(plugin)),
                    HttpResponse.BodyHandlers.ofString(),
                    new Tuple2[]{new Tuple2<>("Engine-Key",engineKey),
                            new Tuple2<>("Cookie", "EditorId=" + editorId),
                            new Tuple2<>("Content-Type", "application/json")});
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        if (res.statusCode() != 200) {
            throw new BadRequestException(res.body());
        }
    }
    private PluginDef parse(String input) throws SubstitutionVisitor.SymbolError {
        return astBuilder.visitIşlevTanımı(Utils.getParser(input).işlevTanımı());
    }
}