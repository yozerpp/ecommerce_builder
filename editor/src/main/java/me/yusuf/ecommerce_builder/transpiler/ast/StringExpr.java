package me.yusuf.ecommerce_builder.transpiler.ast;

// Represents a string literal.
public class StringExpr implements Expression {
    public final String value;

    public StringExpr(String value) {
        this.value = value;
    }
}
