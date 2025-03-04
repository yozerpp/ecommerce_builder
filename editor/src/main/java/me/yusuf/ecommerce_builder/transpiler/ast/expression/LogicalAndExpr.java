package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import java.util.List;
import java.util.Objects;

public class LogicalAndExpr implements Expression {
    public final EqualityExpr first;
    public final List<EqualityExpr> rest; // each joined with the operator "ve"

    public LogicalAndExpr(EqualityExpr first, List<EqualityExpr> rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        LogicalAndExpr that = (LogicalAndExpr) object;
        return first.equals(that.first) && Objects.equals(rest, that.rest);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + Objects.hashCode(rest);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        if (rest!=null)for (EqualityExpr expr : rest) {
            sb.append(" ve ").append(expr.toString());
        }
        return sb.toString();
    }
}
