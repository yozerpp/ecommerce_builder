package me.yusuf.ecommerce_builder.shared.types.plugin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;

@Entity(name = "EntitySource")
@Table(name = "entity_source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntitySource extends Versioned {
    @Embeddable
    public record Id(
        @Column(name = "editor_id", nullable = false, updatable = false)
        Integer editorId,
        @Column(name = "entity_class", nullable = false, updatable = false)
        Class<?> entityClass,
        @Column(name = "version", nullable = false, updatable = false)
        int version
    ) {}
    @EmbeddedId
    private Id id;
    private boolean member = false;
    @Column(name = "char_encoded", nullable = true, updatable = true, columnDefinition = "text")
//    @JdbcTypeCode(SqlTypes.CLOB)
    private String charEncoded;
//    @JdbcTypeCode(SqlTypes.BLOB)
    @Column(name="byte_encoded", nullable = false, updatable = true, columnDefinition = "bytea")
    private byte[] byteEncoded;
    public static final String STATIC_PACKAGE_PREFIX = "me.yusuf.ecommerce_builder.shared.types.entity";
    public static final String DYNAMIC_PACKAGE_PREFIX =
            "me.yusuf.ecommerce_builder.demo.domain.dynamic.entity.editor";
    @Transient
    public static String getClassName(EntitySource es){

        return es.id.version>0?DYNAMIC_PACKAGE_PREFIX + es.id.editorId + '.' +
                ( es.isMember()?
                        es.id.entityClass.getDeclaringClass().getSimpleName() + "_v" + es.id.version + "$" + es.id.entityClass.getSimpleName()
                        :(es.id.entityClass.getSimpleName() + "_v" + es.id.version))
                :STATIC_PACKAGE_PREFIX + '.' + (es.isMember()?es.id.entityClass.getDeclaringClass().getSimpleName()+"$"+ es.id.entityClass.getSimpleName():es.id.entityClass.getSimpleName());
    }
    @Transient
    public static String getClassName(String defaultClassSimpleName, int version,int editorId){
        return version>0?
                DYNAMIC_PACKAGE_PREFIX + editorId + '.' + defaultClassSimpleName + "_v" + version
                :(STATIC_PACKAGE_PREFIX + '.' + defaultClassSimpleName);
    }
}
