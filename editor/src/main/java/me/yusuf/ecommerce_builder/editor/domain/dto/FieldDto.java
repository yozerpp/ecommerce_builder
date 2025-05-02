package me.yusuf.ecommerce_builder.editor.domain.dto;

import java.lang.reflect.Type;

public record FieldDto(Class<?> declaringClass, Type type, String name, int visibility, boolean isUpdatable, boolean isNullable,String defaultValue,boolean isUnique) {
    public enum Visibility{
        PUBLIC,
        PROTECTED,
        PRIVATE,
        PACKAGE_PRIVATE
    }
    public FieldDto(Class<?> declaringClass,Type type, String name){
        this(declaringClass, type,name, 0);
    }
    public FieldDto(Class<?> declaringClass,Type type, String name, int visibility){
        this(declaringClass,type,name, visibility,true, true, null, false);
    }
    public FieldDto(Class<?> declaringClass,Type type, String name, int visibility, boolean isUpdatable, boolean isNullable) {
        this(declaringClass,type, name, visibility, isUpdatable, isNullable, null, false);
    }
    public FieldDto(Class<?> declaringClass,Type type, String name, int visibility, boolean isUpdatable, boolean isNullable, String defaultValue) {
        this(declaringClass, type, name, visibility, isUpdatable, isNullable, defaultValue, false);
    }

}
