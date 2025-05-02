package me.yusuf.ecommerce_builder.shared.types;

import jakarta.persistence.*;

@Entity
public record PluginClassFile(
    @EmbeddedId
    Id id,
    ClassFileObject classFile,
    @Embedded
    PluginMetadata metadata
)implements Plugin {
    public record Dto(Id id, ClassFileObject.Dto classFile,PluginMetadata metadata){
    }
    public Dto toDto(){
        return new Dto(id, classFile.toDto(),metadata);
    }
    public PluginClassFile(Dto dto){
        this(dto.id,ClassFileObject.fromDto(dto.classFile),dto.metadata);
    }
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        PluginClassFile that = (PluginClassFile) object;
        return id().equals(that.id());
    }

    @Override
    public int hashCode() {
        return id().hashCode() *10;
    }
}
