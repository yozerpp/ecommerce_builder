package me.yusuf.ecommerce_builder.transpiler.ast;

// Represents a postfix expression. In this grammar, the only postfix operator is "değil".
public class PostfixExpr implements Expression {
    public final Expression primary;
    public final String operator;

    public PostfixExpr(Expression primary, String operator) {
        this.primary = primary;
        this.operator = operator;
    }
}
