package me.yusuf.ecommerce_builder.shared.types;

import me.yusuf.ecommerce_builder.shared.types.annotation.MethodMetadataAnn;

import java.lang.reflect.Type;

public record MethodMetadata(Type retType, Type[] argTypes, Type[] exceptionTypes, MethodMetadataAnn annotation) {
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
