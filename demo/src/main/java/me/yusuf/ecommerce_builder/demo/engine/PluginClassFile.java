package me.yusuf.ecommerce_builder.demo.engine;

import jakarta.persistence.*;
import me.yusuf.ecommerce_builder.shared.types.ClassFileObject;
import me.yusuf.ecommerce_builder.shared.types.Plugin;
import me.yusuf.ecommerce_builder.shared.types.PluginMetadata;

@Entity
public record PluginClassFile(
    @EmbeddedId
    Id id,
    ClassFileObject classFile,
    @Embedded
    PluginMetadata metadata
)implements Plugin {
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
