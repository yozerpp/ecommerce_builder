package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import java.util.List;

public class Expr implements Expression {
    public final LogicalAndExpr first;
    public final List<LogicalAndExpr> rest; // each joined with the operator "veya"

    public Expr(LogicalAndExpr first, List<LogicalAndExpr> rest) {
        this.first = first;
        this.rest = rest;
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
