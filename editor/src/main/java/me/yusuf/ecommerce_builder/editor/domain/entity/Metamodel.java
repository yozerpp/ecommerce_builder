package me.yusuf.ecommerce_builder.editor.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.yusuf.ecommerce_builder.shared.types.conversion.JpaClassConverter;

import java.lang.reflect.ParameterizedType;
import java.util.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metamodel {
    @Embeddable
    @Getter@Setter@NoArgsConstructor@AllArgsConstructor
    public static class Id{
        @Column(name = "editor_id")
        Integer editorId;
        @Convert(converter = JpaClassConverter.class)
        Class<?> originalEntityClass;
    }
    @EmbeddedId
    private Id id;
    private int version;
    @ManyToOne
    @MapsId("editorId")
    @JoinColumn(name = "editor_id", referencedColumnName = "id", nullable = false)
    private Editor editor;
    @ElementCollection(targetClass = Relation.class,fetch = FetchType.EAGER)
    @NotNull
    private List<Relation> relations = new ArrayList<>();
    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Relation{
        @Enumerated(EnumType.ORDINAL)
        private Type type;
        @ManyToOne(fetch = FetchType.EAGER)
        private Metamodel target;
        public enum Type{
            ONE_TO_ONE, ONE_TO_MANY, MANY_TO_ONE, MANY_TO_MANY
        }
    }

    public static Map<Class<?>, Metamodel> generate(Collection<Class<?>> classes, int editorId){
        return new Builder(editorId).build(classes);
    }
    private static class Builder{
        private final int editorId;
        Builder(int editorId){
            this.editorId = editorId;
        }
        final Map<Class<?>, Metamodel> classesDone = new HashMap<>();
        Map<Class<?>, Metamodel> build(Collection<Class<?>> classes){
            for(var cls : classes){
                build(cls);
            }
            return classesDone;
        }
        private Metamodel build(Class<?> cls){
            Metamodel ret;
            if ((ret = classesDone.get(cls))!=null) return ret;
            ret= new Metamodel(
                    new Id(editorId, cls),
                    0,
                    null,null);
            classesDone.put(cls, ret);
            ret.setRelations(Arrays.stream(cls.getDeclaredFields()).map(f->{
                        Relation.Type type;
                        Class<?> target;
                        if(f.isAnnotationPresent(OneToOne.class)) {
                            type = Relation.Type.ONE_TO_ONE;
                            target = f.getType();
                        }
                        else if (f.isAnnotationPresent(OneToMany.class)) {
                            type= Relation.Type.ONE_TO_MANY;
                            target = (Class<?>) ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0];
                        }
                        else if (f.isAnnotationPresent(ManyToOne.class)) {
                            type =  Relation.Type.MANY_TO_ONE;
                            target = f.getType();
                        }
                        else if (f.isAnnotationPresent(ManyToMany.class)) {
                            type= Relation.Type.MANY_TO_MANY;
                            target = (Class<?>) ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0];
                        }
                        else return null;
                        return new Relation(type, build(target));
                    }).filter(Objects::nonNull).toList()
            );
            return ret;
        }
    }
}
