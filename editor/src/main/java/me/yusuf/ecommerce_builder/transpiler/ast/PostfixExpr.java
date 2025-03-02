package me.yusuf.ecommerce_builder.transpiler.ast;

public class PostfixExpr implements Expression {
    private final Expression primary;
    private final boolean hasNot; // true if the optional DEĞİL token is present

    public PostfixExpr(Expression primary, boolean hasNot) {
        this.primary = primary;
        this.hasNot = hasNot;
    }

    @Override
    public String toString() {
        return primary.toString() + (hasNot ? " değil" : "");
    }
}
