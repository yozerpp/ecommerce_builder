package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.shared.types.plugin.MethodMetadata;
import me.yusuf.ecommerce_builder.shared.types.plugin.Plugin;
import me.yusuf.ecommerce_builder.shared.types.plugin.PluginDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Base64;
import java.util.Map;

@RestController
//@ConditionalOnProperty(prefix = "deployment", name = "type", havingValue = "editor")
@RequestMapping(value = "/engine")
public class EngineController {
    private static final String engineKey= Base64.getEncoder().encodeToString(Sha512DigestUtils.sha("Y&5v9+57bEhq"));
    private final EngineService engineService;
    public EngineController(final EngineService es) {
        this.engineService = es;
    }
    @GetMapping("/metadata")
    public ResponseEntity<?> getMetadata(@RequestParam(required = false) String className) {
        if (className==null) return getMetadata();
        var ret  =engineService.getMethods(className);
        if (ret.isEmpty()) return ResponseEntity.notFound().build(); 
        return ResponseEntity.ok(ret);
    }
    private ResponseEntity<Map<String, Map<String, MethodMetadata>>> getMetadata(){
        var ret = engineService.getMethods();
        if (ret.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ret);
    }
    @PostMapping(value = "/plugin")
    public void addPlugin(@RequestBody PluginDto pluginClassFile/*added Objects name needs to have editorId as prefix*/
    ,@RequestHeader("Engine-Key") String engineKey) {
        if (!engineKey.equals(EngineController.engineKey)) throw HttpClientErrorException.Unauthorized.create(HttpStatusCode.valueOf(401),"Invalid Engine Key.",null,null,null);
        engineService.addPlugin(new Plugin(pluginClassFile));
    }
    @PostMapping(value = "/field")
    public void addField(@RequestParam int editorId,@RequestBody FieldRequest reqBody){
        engineService.replaceEntities(reqBody.entitySources, reqBody.pluginSources, editorId);
    }
    public record FieldRequest(EntitySource[] entitySources, PluginDto[] pluginSources){ }
}
