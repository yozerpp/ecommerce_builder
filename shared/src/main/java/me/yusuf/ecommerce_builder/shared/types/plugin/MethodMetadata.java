package me.yusuf.ecommerce_builder.shared.types.plugin;

import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;

import java.lang.reflect.Type;

public record MethodMetadata(Type retType, Type[] argTypes, Type[] exceptionTypes, MethodInfo annotation) {
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        MethodMetadata that = (MethodMetadata) object;
        return annotation().equals(that.annotation());
    }

    @Override
    public int hashCode() {
        return annotation().hashCode();
    }
}
