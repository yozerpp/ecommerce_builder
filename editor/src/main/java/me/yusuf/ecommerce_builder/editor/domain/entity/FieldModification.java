package me.yusuf.ecommerce_builder.editor.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce_builder.shared.types.conversion.JpaTypeConverter;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;
import org.hibernate.annotations.ColumnDefault;

import java.lang.reflect.Type;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldModification extends Versioned {
    @EmbeddedId
    private Id id;
    @Embedded
    private Field field;
    @JoinColumn(name = "editor_id",insertable = false, updatable = false)
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Editor editor;
    @Embeddable
    public record Id(
            @Column(name = "editor_id", updatable = false,nullable = false)
            Integer editorId,
            @Column(name = "entity_class",nullable = false,updatable = false)
            Class<?> entityClass,
            @Column(name = "field_name", nullable = false, updatable = false)
            String fieldName
    ) {
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Id that)) return false;
            return editorId.equals(that.editorId) &&
                    entityClass.getName().equals(that.entityClass.getName()) && fieldName.equals(that.fieldName);
        }
        @Override
        public int hashCode() {
            return Objects.hash(editorId, entityClass.getName(),fieldName);
        }
    }
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Field{
        @Column(name = "field_type", nullable = false)
        @Convert(converter = JpaTypeConverter.class)
        private Type type;
        @ColumnDefault("true")
        @Column(name = "field_nullable",nullable = false)
        private Boolean nullable;
        @ColumnDefault("true")
        @Column(name = "field_updatable", nullable = false)
        private Boolean updatable;
        @ColumnDefault("false")
        @Column(name = "field_unique", nullable = false)
        private Boolean unique;
        @Column(name= "field_default", nullable = true)
        private String defaultValue;
        @Transient
        private boolean isCollection(){
            return type instanceof Class<?>c && (c.isArray() || Iterable.class.isAssignableFrom(c));
        }
    }
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof FieldModification that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}