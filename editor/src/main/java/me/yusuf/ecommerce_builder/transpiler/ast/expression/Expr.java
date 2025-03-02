package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import java.util.List;

public class Expr implements Expression {
    private final LogicalAndExpr first;
    private final List<LogicalAndExpr> rest; // each joined with the operator "veya"

    public Expr(LogicalAndExpr first, List<LogicalAndExpr> rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        for (LogicalAndExpr expr : rest) {
            sb.append(" veya ").append(expr.toString());
        }
        return sb.toString();
    }
}
