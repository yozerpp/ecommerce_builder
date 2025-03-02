package me.yusuf.ecommerce_builder.transpiler.ast;

import java.util.List;

public class LogicalAndExpr implements Expression {
    private final EqualityExpr first;
    private final List<EqualityExpr> rest; // each joined with the operator "ve"

    public LogicalAndExpr(EqualityExpr first, List<EqualityExpr> rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        for (EqualityExpr expr : rest) {
            sb.append(" ve ").append(expr.toString());
        }
        return sb.toString();
    }
}
