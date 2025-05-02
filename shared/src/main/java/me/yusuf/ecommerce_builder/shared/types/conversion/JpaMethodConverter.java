package me.yusuf.ecommerce_builder.shared.types.conversion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.lang.reflect.Method;

@Converter(autoApply = true)
public class JpaMethodConverter implements AttributeConverter<Method, String> {
    @Override
    public String convertToDatabaseColumn(Method attribute) {
        return attribute.getDeclaringClass().getName() + "#" +attribute.getName();
    }

    @Override
    public Method convertToEntityAttribute(String dbData) {
        var splt = dbData.split("#");
        try {
            return Class.forName(splt[0]).getMethod(splt[1]);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
