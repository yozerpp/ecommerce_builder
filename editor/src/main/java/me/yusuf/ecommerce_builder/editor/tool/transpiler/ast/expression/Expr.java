package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression;

import java.util.List;
import java.util.Objects;

/**
 * @param rest each joined with the operator "veya"
 */
public record Expr(LogicalAndExpr first, List<LogicalAndExpr> rest) implements Expression {

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Expr expr = (Expr) object;
        return first.equals(expr.first) && Objects.equals(rest, expr.rest);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        if (rest != null) for (LogicalAndExpr expr : rest) {
            sb.append(" veya ").append(expr.toString());
        }
        return sb.toString();
    }
}
