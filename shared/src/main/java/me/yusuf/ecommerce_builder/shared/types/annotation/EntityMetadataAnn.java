package me.yusuf.ecommerce_builder.shared.types.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EntityMetadataAnn {
    String name();
    String description();
    record Impl(String name, String description) implements EntityMetadataAnn{
        @Override
        public Class<? extends Annotation> annotationType() {
            return EntityMetadataAnn.class;
        }
    }
}
