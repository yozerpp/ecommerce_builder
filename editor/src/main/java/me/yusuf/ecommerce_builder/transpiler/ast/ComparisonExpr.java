package me.yusuf.ecommerce_builder.transpiler.ast;

import java.util.List;

public class ComparisonExpr implements Expression {
    private final AdditiveExpr first;

    public static class Op {
        public final String operator; // one of ">", "<", ">=", "<="
        public final AdditiveExpr expr;

        public Op(String operator, AdditiveExpr expr) {
            this.operator = operator;
            this.expr = expr;
        }

        @Override
        public String toString() {
            return " " + operator + " " + expr.toString();
        }
    }

    private final List<Op> ops;

    public ComparisonExpr(AdditiveExpr first, List<Op> ops) {
        this.first = first;
        this.ops = ops;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        for (Op op : ops) {
            sb.append(op.toString());
        }
        return sb.toString();
    }
}
