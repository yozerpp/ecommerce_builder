package me.yusuf.ecommerce_builder.editor.helper;

import me.yusuf.ecommerce_builder.shared.components.repository.EntitySourceRepository;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class Importer {
    private final EntitySourceRepository entitySourceRepository;
    public Importer(EntitySourceRepository entitySourceRepository) {
        this.entitySourceRepository = entitySourceRepository;
    }
    public String getImports(int editorId, Class<?>... classesToImport ){
        Integer ver;
        if ((ver=entitySourceRepository.findLatestVersionForEditor(editorId))==null)
            return Arrays.stream(classesToImport).map(c->"import " + c.getName() + ";\n").reduce("" , String::concat);
        else return Arrays.stream(classesToImport).map(c->"import " + EntitySource.getClassName(c.getSimpleName(),ver,editorId) + ";\n").reduce("", String::concat);

    }
}
