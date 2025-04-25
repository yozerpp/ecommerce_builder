package me.yusuf.ecommerce_builder.editor.network;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EditorController {
    private final CodeGeneratorService codeGeneratorService;
    public EditorController(CodeGeneratorService codeGeneratorService) {
        this.codeGeneratorService = codeGeneratorService;
    }
    @PostMapping("/plugin")//TODO: error reporting
     public void createPlugin(@RequestParam String source) throws BadRequestException {
        codeGeneratorService.sendToApp(codeGeneratorService.generate(source));
    }
}
