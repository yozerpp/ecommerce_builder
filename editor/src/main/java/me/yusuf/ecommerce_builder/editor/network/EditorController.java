package me.yusuf.ecommerce_builder.editor.network;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EditorController {
    private final CodeGeneratorService codeGeneratorService;
    public EditorController(CodeGeneratorService codeGeneratorService) {
        this.codeGeneratorService = codeGeneratorService;
    }
    @PostMapping("/plugin")//TODO: error reporting
     public void createPlugin(@RequestBody String source) throws BadRequestException {
        codeGeneratorService.sendToApp(codeGeneratorService.generate(source));
    }
}
