package me.yusuf.ecommerce_builder.shared.types.conversion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import me.yusuf.utils.ReflectionUtils;

import java.lang.reflect.Type;

@Converter
public class JpaTypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type attribute) {
        return ReflectionUtils.typeToString(attribute);
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        try {
            return ReflectionUtils.typeForName(dbData);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
