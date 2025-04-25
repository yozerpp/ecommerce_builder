package me.yusuf.ecommerce_builder.editor.transpiler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Completer {
//    public ICompletion[] complete(TurkishPseudoCodeParser.IşlevTanımıContext def, CodeCompletionCore completionCore, int caretPos) {
//        var methodAndMetadata = registry.getMethodAndMetadata(def.işlevİsmi().getText(),def.varlıkİsmi().getText());
//        var candidates = completionCore.collectCandidates(caretPos,def);
//        candidates.rules.entrySet().stream().map(e->{
//            switch (e.getKey()){
//                case TurkishPseudoCodeParser.RULE_varlıkİsmi ->
//            }
//        })
//    }
//    public EntityCompletion[] getAllEntityCompletions(){
//        return Arrays.stream(registry.getEntityClasses()).map(this::getEntityCompletion).toArray(EntityCompletion[]::new);
//    }
//    public EntityCompletion getEntityCompletion(Class<?> cls){
//        return new EntityCompletion(
//                cls.getSimpleName(),
//                cls.isAnnotationPresent(EntityMetadataAnn.class)? cls.getAnnotation(EntityMetadataAnn.class).description(): "",
//                false,
//                getFieldCompletions(cls)
//        );
//    }
//    public FieldCompletion[] getFieldCompletions(Class<?> cls){
//        return Arrays.stream(cls.getDeclaredFields()).map(f->
//                new FieldCompletion(f.getName(),
//                        f.getType().getSimpleName(),
//                        f.isAnnotationPresent(FieldMetadataAnn.class)? f.getAnnotation(FieldMetadataAnn.class).description() : "",
//                        f.getType().isAnnotationPresent(Entity.class)? getEntityCompletion(f.getType()):null)
//        ).toArray(FieldCompletion[]::new);
//    }
//    public ISymbolCompletion[] getReferencableSymbolCompletions(MethodMetadata metadata){
//        var argTypes = metadata.argTypes();
//        Arrays.stream(argTypes).map(tp->((Class<?>)tp).isAnnotationPresent(Entity.class)?getEntityCompletion((Class<?>)tp):null);
//    }
}
