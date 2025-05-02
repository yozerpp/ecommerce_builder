package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression;

import java.util.Objects;

/**
 * @param operator If operator is non-null, this node comes from the ('-' | DEĞİL) unaryExpr alternative. If operator is null, then operand is actually a PostfixExpr. e.g. "-" or "değil"
 * @param operand  either a UnaryExpr or a PostfixExpr
 */
public record UnaryExpr(String operator, PostfixExpr operand) implements Expression {

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        UnaryExpr unaryExpr = (UnaryExpr) object;
        return Objects.equals(operator, unaryExpr.operator) && operand.equals(unaryExpr.operand);
    }

    @Override
    public String toString() {
        return (operator!=null?operator:"") + (operand != null ? operand.toString() : "null");
    }
}
