package me.yusuf.ecommerce_builder.editor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import me.yusuf.ecommerce_builder.editor.domain.entity.Metamodel;
import me.yusuf.ecommerce_builder.editor.helper.ReferenceManager;
import me.yusuf.ecommerce_builder.editor.helper.Utils;
import me.yusuf.ecommerce_builder.shared.components.repository.PluginRepository;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.editor.domain.entity.FieldModification;
import me.yusuf.ecommerce_builder.shared.components.repository.EntitySourceRepository;
import me.yusuf.ecommerce_builder.editor.domain.repository.FieldModificationRepository;
import me.yusuf.ecommerce_builder.editor.tool.compiler.DynamicCompiler;
import me.yusuf.ecommerce_builder.editor.helper.DemoClient;
import me.yusuf.ecommerce_builder.editor.domain.dto.FieldDto;
import me.yusuf.ecommerce_builder.shared.types.plugin.PluginDto;
import me.yusuf.utils.ReflectionUtils;
import me.yusuf.utils.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * enemy of the humans and computers alike...
 */
@Component
public class EntityModifierService {
    private final DemoClient demoClient = new DemoClient("engine/field");
    private final FieldModificationRepository fieldModificationRepository;
    private final EntitySourceRepository entitySourceRepository;
    private final ObjectMapper objectMapper;
    private final Class<?>[] defaultEntityClasses;
    private final CodeGeneratorService codeGeneratorService;
    private final PluginRepository pluginRepository;
    private final EntityManager entityManager;
    private final Map<Class<?>, Metamodel> defaultMetamodels;
    public EntityModifierService(Map<Class<?>, Metamodel> defaultMetamodels, Class<?>[] defaultEntityClasses, EntityManager entityManager, FieldModificationRepository fieldModificationRepository, EntitySourceRepository entitySourceRepository, ObjectMapper objectMapper, CodeGeneratorService codeGeneratorService, PluginRepository pluginRepository){
        this.objectMapper = objectMapper;
        this.entityManager = entityManager;
        this.defaultMetamodels = defaultMetamodels;
        this.defaultEntityClasses = defaultEntityClasses;
        this.fieldModificationRepository = fieldModificationRepository;
        this.entitySourceRepository = entitySourceRepository;
        this.codeGeneratorService = codeGeneratorService;
        this.pluginRepository = pluginRepository;
    }
    public void addFields(FieldDto[] fieldDtos, int editorId) {
        final Integer version = entitySourceRepository.findLatestVersionForEditor(editorId);
        final int newVersion = version==null?1:version+1;
        final var refUpdater = new ReferenceManager(defaultEntityClasses, editorId, version==null?0:version);
        var sources = entitySourceRepository.findLatestVersions(editorId);
        sources.addAll(Arrays.stream(defaultEntityClasses)
                .filter(c->sources.stream().noneMatch(s->s.getId().entityClass().getName().equals(c.getName())) && c.getDeclaringClass()==null)
                .map(c-> new EntitySource(new EntitySource.Id(editorId,c,0), false, Utils.getStaticClassSource(c),null))
                .toList());
        var linkedSources = new ArrayList<>(sources.stream().filter(s->!s.isMember()).map(s->{
            Class<?> entityClass =s.getId().entityClass();
            EntitySource es = getSource(entityClass, editorId, newVersion);
            String source = es.getCharEncoded();
            String[] finalSource = {source};
            var o =Arrays.stream(fieldDtos).filter(f->f.declaringClass().isAssignableFrom(entityClass)).findAny().map(f->addFieldDefinition(finalSource[0],f));
            if (o.isPresent()) source = o.get();
            es.setCharEncoded( refUpdater.update(source, entityClass.getName()));
            es.setId(new EntitySource.Id(editorId,entityClass,newVersion));
            return es;
        }).toList());
        var compilerInput = linkedSources.stream().map(s->new DynamicCompiler.SourceFile(EntitySource.getClassName(s),s.getCharEncoded())).collect(Collectors.toList());
        Collections.addAll(compilerInput, Arrays.stream(defaultEntityClasses).filter(d->d.getDeclaringClass()==null).map(c->new DynamicCompiler.SourceFile(c.getName(), Utils.getStaticClassSource(c).replaceFirst("@Entity(\\(.*\\))?","@MappedSuperclass"))).toArray(DynamicCompiler.SourceFile[]::new));
        var cobs = DynamicCompiler.compile(compilerInput.toArray(DynamicCompiler.SourceFile[]::new));
        cobs.stream().filter(cob->cob.getClassName().matches("^.*\\.\\w+_v\\d+$")).forEach(cob->{
            linkedSources.stream().filter(s->s.getId().entityClass().getSimpleName().equals(cob.getClassName().replaceAll("(\\w+\\.)+","").replaceFirst("_v\\d+$","")))
                    .findAny().get().setByteEncoded(cob.getClassBytes());
        });
        var recompiledPlugins = codeGeneratorService.recompilePlugins(editorId, linkedSources);
        try {
            sendToDemo(editorId, linkedSources, recompiledPlugins);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        entityManager.clear();
        Arrays.asList(recompiledPlugins).forEach(rp->{
            entityManager.detach(rp);
            pluginRepository.save(rp);
        });
        linkedSources.forEach(s->{
            entityManager.detach(s);
            entitySourceRepository.save(s);
        });
        saveFieldModifications(fieldDtos, editorId);
    }

    private void saveFieldModifications(FieldDto[] fieldDtos, int editorId) {
        for (var fdto : fieldDtos){
            fieldModificationRepository.save(
                new FieldModification(
                    new FieldModification.Id(editorId,fdto.declaringClass(),fdto.name()),
                    new FieldModification.Field(fdto.type(),
                        fdto.isNullable(),
                        fdto.isUpdatable(),
                        fdto.isUnique(),
                        fdto.defaultValue()
                    ),
                    null
                ));
        }
    }

    private void sendToDemo(int editorId, List<EntitySource> compiledSources, PluginDto[] recompiledPlugins) throws IOException, InterruptedException {
        var res = demoClient.send("?editorId="+ editorId,"POST",
                HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(Map.of("entitySources", compiledSources, "pluginSources", recompiledPlugins))),
                HttpResponse.BodyHandlers.ofString());
        if (res.statusCode()>300){
            throw new RuntimeException("Engine failed to register classes. Response body: " + res.body());
        }
    }
    private EntitySource getSource(Class<?> entityClass, int editorId, Integer version) {
        return new EntitySource(new EntitySource.Id(editorId,entityClass,version!=null?version:0),false,"""
                package %s;
                import lombok.*;
                import jakarta.persistence.*;
                @Getter@Setter@NoArgsConstructor
                @Entity(name="%s")
                public class %s extends %s{
                }
                """.formatted(
                        EntitySource.DYNAMIC_PACKAGE_PREFIX + editorId,
                entityClass.getSimpleName() + "_v" + (version!=null?version:0),
//                "demo" + editorId,
                entityClass.getSimpleName() + "_v" + (version!=null?version:0),
                entityClass.getName())
        ,null);
        //        return (version==null||version.equals(0))?
//                new EntitySource(new EntitySource.Id(editorId,entityClass,0),false,getStaticClassSource(entityClass),null)
//                :entitySourceRepository.findById(new EntitySource.Id(editorId,entityClass,version));
//
    }

    private static String addFieldDefinition(String source, FieldDto fieldDto){
//        if (fieldDto.type() instanceof Class<?> cls && !cls.isPrimitive()){
//            source = addTypeImport(source, cls);
//        }
        return source.substring(0, source.lastIndexOf("}")) + "\n\t" +
                "@Column(name=\"" + StringUtils.toSnakeCase(fieldDto.name()) + "\", updatable=" + (fieldDto.isUpdatable()?"true":"false") +
                    ", nullable=" + (fieldDto.isNullable()?"true":"false") + ", unique=" + (fieldDto.isUnique()?"true":"false") + ")\n" +
                (fieldDto.defaultValue()!=null?"@org.hibernate.annotations.ColumnDefault(\"" + fieldDto.defaultValue() +"\")\n":"")+
                createFieldDecl(fieldDto) + ";\n"+
                "}";
    }
//    private static String addTypeImport(String source, Class<?> cls){
//        Pattern conflictingClassName = Pattern.compile("import\\s+(\\w+\\.)+" + cls.getSimpleName() + "\\s*;", Pattern.MULTILINE | Pattern.DOTALL);
//        Pattern alreadyImported = Pattern.compile("import\\s+" + cls.getName() + "\\s*;", Pattern.MULTILINE | Pattern.DOTALL);
//        if (alreadyImported.matcher(source).find())return source;
//        else if (conflictingClassName.matcher(source).find())
//            return source.
//    }
    /**
     * Field type is fully qualified, no imports.
     */
    private static String createFieldDecl(FieldDto fieldDto){
        return (fieldDto.isNullable()?"@Nullable ":"") + FieldDto.Visibility.values()[fieldDto.visibility()].name().toLowerCase()+ " "  + ReflectionUtils.toGenericString(fieldDto.type()) +" "+ fieldDto.name();
    }
    private static String createGetterAndSetter(FieldDto fieldDto){
        var typeStr = ReflectionUtils.toGenericString(fieldDto.type());
        return "\tpublic "+typeStr+ " get" + StringUtils.firstLetterToUpperCase(fieldDto.name()) + "(){\n\t\treturn "
                +fieldDto.name() + ";\n\t}\n" +
                "\tpublic void set"+ StringUtils.firstLetterToUpperCase(fieldDto.name()) + '('+ typeStr + " arg){\n"+
                "\t\tthis."+ fieldDto.name() + "=arg;\n\t}\n";
    }
}
