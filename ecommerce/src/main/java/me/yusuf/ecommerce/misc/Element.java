package me.yusuf.ecommerce.misc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Element {
    String label() default "";
    String tag() default "div";
    String[] classNames() default {};
    String[] attributes() default {}; //"key=value;key=value"
    String[] style() default {};
    String id() default "";
    int[] containerIds() default {};
    String[] containerClassNames() default {};
    boolean hideNull() default true;
    boolean required() default false;
    PropertyType type() default PropertyType.STRING;
}
