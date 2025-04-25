package me.yusuf.ecommerce_builder.shared.types;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import me.yusuf.ecommerce_builder.shared.types.conversion.JDBCTypeConverter;

import java.lang.reflect.Type;
@Embeddable
public record PluginMetadata(
        @Convert(converter = JDBCTypeConverter.class)
        Type[] argTypes) {
}
