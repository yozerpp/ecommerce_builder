package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression;

import java.util.List;
import java.util.Objects;

/**
 * @param rest each joined with the operator "ve"
 */
public record LogicalAndExpr(EqualityExpr first, List<EqualityExpr> rest) implements Expression {

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        LogicalAndExpr that = (LogicalAndExpr) object;
        return first.equals(that.first) && Objects.equals(rest, that.rest);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        if (rest != null) for (EqualityExpr expr : rest) {
            sb.append(" ve ").append(expr.toString());
        }
        return sb.toString();
    }
}
