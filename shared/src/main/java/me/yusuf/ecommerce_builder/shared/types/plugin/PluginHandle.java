package me.yusuf.ecommerce_builder.shared.types.plugin;

import java.lang.reflect.Method;

public record PluginHandle(IPlugin.Id id, PluginMetadata metadata, Method handle)implements IPlugin {
    @Override
    public Id getId() {
        return id;
    }

    @Override
    public PluginMetadata getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        PluginHandle that = (PluginHandle) object;
        return id().equals(that.id()) ;
    }

    @Override
    public int hashCode() {
        return id().hashCode();
    }
}
