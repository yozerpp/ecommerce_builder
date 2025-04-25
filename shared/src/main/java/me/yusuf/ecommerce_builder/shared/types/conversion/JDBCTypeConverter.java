package me.yusuf.ecommerce_builder.shared.types.conversion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Converter(autoApply = true)
public class JDBCTypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type attribute) {
        return ((Class<?>)attribute).getName();
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        try {
            return Class.forName(dbData);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
