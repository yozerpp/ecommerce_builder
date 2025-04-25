package me.yusuf.ecommerce_builder.shared.types.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMetadataAnn {
    String description();
    record FieldMetadataAnnImpl(String description) implements FieldMetadataAnn {
        @Override
        public Class<? extends Annotation> annotationType() {
            return FieldMetadataAnn.class;
        }
    }
}
