package me.yusuf.ecommerce_builder.shared.types.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MethodInfo {
    String userFriendlyName() default "";
    Class<?>[] operatedEntities() default {};
   public record Impl(String userFriendlyName, Class<?>[] operatedEntities) implements MethodInfo {
        @Override
        public Class<? extends Annotation> annotationType() {
            return MethodInfo.class;
        }
    }
}
