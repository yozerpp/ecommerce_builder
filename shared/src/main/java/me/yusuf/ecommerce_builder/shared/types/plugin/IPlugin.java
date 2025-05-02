package me.yusuf.ecommerce_builder.shared.types.plugin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import me.yusuf.ecommerce_builder.shared.types.conversion.JpaTypeConverter;
import org.springframework.data.rest.core.config.Projection;

import java.io.Serializable;
import java.lang.reflect.Type;

@Projection(name = "PluginWithoutSource", types = PluginDto.class)
public interface IPlugin extends Serializable {
    Id getId();
    PluginMetadata getMetadata();
    boolean equals(Object o);
    int hashCode();
    String PLUGIN_PACKAGE_PREFIX="me.yusuf.ecommerce_builder.demo.engine.plugin";
    @Transient
    @JsonIgnore
    default String getClassName(){
        return PLUGIN_PACKAGE_PREFIX + "." + getId().getName() + "Plugin_" +  getId().editorId + "_v" + getId().version;
    }
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class Id implements Serializable{
        @Column(name = "editor_id")
         int editorId;
        @Column(name = "name")
         String name;
        @Column(name = "hooked_method")
         String hookedMethod;
        @Column(name = "version")
         int version;
        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;
            Id id = (Id) object;
            return editorId == id.editorId && name.equals(id.name) && hookedMethod.equals(id.hookedMethod);
        }
        @Override
        public int hashCode() {
            int result = editorId;
            result = 31 * result + name.hashCode();
            result = 27*result + hookedMethod.hashCode();
            return 3*result + version;
        }
    }
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class PluginMetadata implements Serializable{
        @Convert(converter = JpaTypeConverter.class)
        @Column(name = "argument_types")
        private Type[] argumentTypes;
        @Transient
        @JsonIgnore
        public Type[] argTypes() {
            return argumentTypes;
        }
    }
}
