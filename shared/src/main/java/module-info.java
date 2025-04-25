module shared {
    requires utils;
    requires com.fasterxml.jackson.databind;
    requires jakarta.annotation;
    requires jakarta.servlet;
    requires jakarta.persistence;
    requires static lombok;
    requires java.compiler;
    exports me.yusuf.ecommerce_builder.shared.types;
    exports me.yusuf.ecommerce_builder.shared.types.tuple;
    exports me.yusuf.ecommerce_builder.shared.types.annotation;
    exports me.yusuf.ecommerce_builder.shared.components;
    exports me.yusuf.ecommerce_builder.shared.types.conversion;
}