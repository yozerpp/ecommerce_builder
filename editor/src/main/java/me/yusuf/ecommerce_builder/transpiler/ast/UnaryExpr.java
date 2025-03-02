package me.yusuf.ecommerce_builder.transpiler.ast;

// Represents a unary operation, for prefix operators such as '-' or 'değil'.
public class UnaryExpr implements Expression {
    public final Expression operand;
    public final String operator;

    public UnaryExpr(Expression operand, String operator) {
        this.operand = operand;
        this.operator = operator;
    }
}
