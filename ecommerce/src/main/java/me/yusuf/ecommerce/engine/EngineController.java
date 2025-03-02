package me.yusuf.ecommerce.engine;

import me.yusuf.ecommerce.engine.classloading.DynamicCompiler;
import me.yusuf.ecommerce.utils.exception.NotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
//@ConditionalOnProperty(prefix = "deployment", name = "type", havingValue = "editor")
@RequestMapping("/engine")
public class EngineController {
    static final String PLUGIN_PACKAGE = "me.yusuf.ecommerce.engine.plugins";
    @PostMapping("/plugin")
    public ResponseEntity<String> addPlugin(@RequestBody AddPluginParams params) throws NotFoundException {
        var cls =  DynamicCompiler.compile(params.imports,params.pluginName, params.pluginSourceCode);
        var pluginMethod = Arrays.stream(cls.getDeclaredMethods()).findFirst().orElseThrow(() -> new NotFoundException("There is no method in the input."));
        pluginMethod.setAccessible(true);
        try {
            PluginRegistry.registerPlugin(EditorContextHolder.getUserId(), params.afterMethod, pluginMethod);
        } catch (PluginRegistry.IncompatibleMethodSignatureException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
    public static class AddPluginParams {
        public String afterMethod;
        public String pluginName;
        public String pluginSourceCode;
        public String[] imports;
    }
}
