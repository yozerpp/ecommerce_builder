package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import java.util.List;

public class EqualityExpr implements Expression {
    public final ComparisonExpr first;

    public static class Op {
        public final String operator; // either "==" or "!="
        public final ComparisonExpr expr;

        public Op(String operator, ComparisonExpr expr) {
            this.operator = operator;
            this.expr = expr;
        }

        @Override
        public String toString() {
            return " " + operator + " " + expr.toString();
        }
    }

    public final List<Op> ops;

    public EqualityExpr(ComparisonExpr first, List<Op> ops) {
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
