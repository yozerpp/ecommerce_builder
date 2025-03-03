package me.yusuf.ecommerce_builder.transpiler.ast.expression;

public class PostfixExpr implements Expression {
    public final Expression primary;
    public final boolean hasNot; // true if the optional DEĞİL token is present

    public PostfixExpr(Expression primary, boolean hasNot) {
        this.primary = primary;
        this.hasNot = hasNot;
    }

    @Override
    public String toString() {
        return primary.toString() + (hasNot ? " değil" : "");
    }
}
