package me.yusuf.ecommerce_builder.shared.types;

import jakarta.persistence.*;
import me.yusuf.ecommerce_builder.shared.types.conversion.JDBCMethodConverter;

import java.lang.reflect.Method;

public interface Plugin {
    public Id id();
    PluginMetadata metadata();
    boolean equals(Object o);
    int hashCode();
    @Embeddable
    public record Id(
            int editorId,
             String name,
             @Convert(converter = JDBCMethodConverter.class)
             Method hookedMethod){
        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;
            Id id = (Id) object;
            return editorId() == id.editorId() && name().equals(id.name()) && hookedMethod().equals(id.hookedMethod());
        }
        @Override
        public int hashCode() {
            int result = editorId();
            result = 31 * result + name().hashCode();
            return 27*result + hookedMethod().hashCode();
        }
    }
}
