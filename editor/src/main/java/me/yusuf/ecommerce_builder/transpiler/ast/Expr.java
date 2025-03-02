package me.yusuf.ecommerce_builder.transpiler.ast;

public class Expr implements Expression {
    private final LogicalOrExpr logicalOrExpr;

    public Expr(LogicalOrExpr logicalOrExpr) {
        this.logicalOrExpr = logicalOrExpr;
    }

    @Override
    public String toString() {
        return logicalOrExpr.toString();
    }
}
