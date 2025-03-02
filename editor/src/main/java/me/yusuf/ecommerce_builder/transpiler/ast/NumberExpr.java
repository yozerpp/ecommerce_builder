package me.yusuf.ecommerce_builder.transpiler.ast;

// Represents a numeric literal.
public class NumberExpr implements Expression {
    public final double value;

    public NumberExpr(double value) {
        this.value = value;
    }
}
