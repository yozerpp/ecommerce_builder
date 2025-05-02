package me.yusuf.ecommerce_builder.shared.types.conversion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import me.yusuf.utils.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Converter
public class JpaClassConverter implements AttributeConverter<Class<?>, String> {
    @Override
    public String convertToDatabaseColumn(Class<?> attribute) {
        return attribute.getName();
    }

    @Override
    public Class<?> convertToEntityAttribute(String dbData) {
        try {
            return ReflectionUtils.classForName(dbData);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
