package me.yusuf.ecommerce_builder.editor.transpiler.ast.expression;

import java.util.Objects;

public class UnaryExpr implements Expression {
    // If operator is non-null, this node comes from the ('-' | DEĞİL) unaryExpr alternative.
    // If operator is null, then operand is actually a PostfixExpr.
    public String operator; // e.g. "-" or "değil"
    public Expression operand; // either a UnaryExpr or a PostfixExpr

    public UnaryExpr(String operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        UnaryExpr unaryExpr = (UnaryExpr) object;
        return Objects.equals(operator, unaryExpr.operator) && operand.equals(unaryExpr.operand);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(operator);
        result = 31 * result + operand.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if (operator == null) {
            return operand != null ? operand.toString() : "null";
        }
        return "(" + operator + " " + (operand != null ? operand.toString() : "null") + ")";
    }
}
