package me.yusuf.ecommerce_builder.transpiler.ast;

import java.util.List;

public class LogicalOrExpr implements Expression {
    private final LogicalAndExpr first;
    private final List<LogicalAndExpr> rest; // each joined with the operator "veya"

    public LogicalOrExpr(LogicalAndExpr first, List<LogicalAndExpr> rest) {
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
