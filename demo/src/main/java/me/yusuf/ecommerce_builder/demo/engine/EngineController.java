package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.shared.types.MethodMetadata;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@ConditionalOnProperty(prefix = "deployment", name = "type", havingValue = "editor")
@RequestMapping("/engine")
public class EngineController {
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
    public ResponseEntity<Map<String, List<Tuple2<String, MethodMetadata>>>> getMetadata(){
        var ret = engineService.getMethods();
        if (ret.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ret);
    }
    @PostMapping("/plugin")
    public void addPlugin(@RequestBody PluginClassFile classFile/*added Objects name needs to have editorId as prefix*/) {
        engineService.addPlugin(classFile);
    }
}
