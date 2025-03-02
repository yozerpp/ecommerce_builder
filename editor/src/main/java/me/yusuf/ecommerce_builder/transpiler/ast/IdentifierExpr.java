package me.yusuf.ecommerce_builder.transpiler.ast;

// Represents an identifier.
public class IdentifierExpr implements Expression {
    public final String name;

    public IdentifierExpr(String name) {
        this.name = name;
    }
}
