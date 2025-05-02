package me.yusuf.ecommerce_builder.editor.network;

import me.yusuf.ecommerce_builder.editor.service.CodeGeneratorService;
import me.yusuf.ecommerce_builder.editor.domain.dto.FieldDto;
import me.yusuf.ecommerce_builder.editor.service.EntityModifierService;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import static me.yusuf.ecommerce_builder.editor.network.ControllerBase.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/demo")
public class DemoController extends ControllerBase {
    private final CodeGeneratorService codeGeneratorService;
    private final EntityModifierService entityModifierService;

    public DemoController(CodeGeneratorService codeGeneratorService, EntityModifierService entityModifierService) {
        this.codeGeneratorService = codeGeneratorService;
        this.entityModifierService = entityModifierService;
    }
    @PostMapping("/plugin")//TODO: error reporting
     public void createPlugin(@RequestBody String source, @CookieValue("editorId") int editorId) throws BadRequestException {
        codeGeneratorService.createPlugin(source, editorId);
    }
    @PostMapping("/field")
    public void createField(@RequestBody FieldDto[] fieldDto, @CookieValue("editorId") int editorId) throws NotFoundException {
        entityModifierService.addFields(fieldDto, editorId);
    }
}
