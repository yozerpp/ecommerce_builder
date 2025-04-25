package me.yusuf.ecommerce_builder.shared.types;

import java.lang.reflect.Method;
import java.util.Objects;

public record PluginMethod(Plugin.Id id, PluginMetadata metadata, Method method)implements Plugin {
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        PluginMethod that = (PluginMethod) object;
        return id().equals(that.id()) ;
    }

    @Override
    public int hashCode() {
        return id().hashCode();
    }
}
