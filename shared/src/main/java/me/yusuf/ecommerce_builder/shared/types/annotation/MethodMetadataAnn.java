package me.yusuf.ecommerce_builder.shared.types.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MethodMetadataAnn {
    String methodFriendlyName() default "";
    Class<?> entity() default Void.class;
   public record Impl(String methodFriendlyName, Class<?> entity) implements MethodMetadataAnn {
        @Override
        public Class<? extends Annotation> annotationType() {
            return MethodMetadataAnn.class;
        }
    }
}
