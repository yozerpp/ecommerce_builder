package me.yusuf.ecommerce_builder.transpiler.ast.expression;

public class UnaryExpr implements Expression {
    // If operator is non-null, this node comes from the ('-' | DEĞİL) unaryExpr alternative.
    // If operator is null, then operand is actually a PostfixExpr.
    private final String operator; // e.g. "-" or "değil"
    private final Expression operand; // either a UnaryExpr or a PostfixExpr

    public UnaryExpr(String operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String toString() {
        if (operator == null) {
            return operand.toString();
        }
        return "(" + operator + " " + operand.toString() + ")";
    }
}
