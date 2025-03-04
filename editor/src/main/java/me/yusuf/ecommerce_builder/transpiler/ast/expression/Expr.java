package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import java.util.List;
import java.util.Objects;

public class Expr implements Expression {
    public final LogicalAndExpr first;
    public final List<LogicalAndExpr> rest; // each joined with the operator "veya"

    public Expr(LogicalAndExpr first, List<LogicalAndExpr> rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Expr expr = (Expr) object;
        return first.equals(expr.first) && Objects.equals(rest, expr.rest);
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
        if (rest!=null)for (LogicalAndExpr expr : rest) {
            sb.append(" veya ").append(expr.toString());
        }
        return sb.toString();
    }
}
