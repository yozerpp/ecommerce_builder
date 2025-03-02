package me.yusuf.ecommerce_builder.transpiler.ast;

import java.util.List;

public class MultiplicativeExpr implements Expression {
    private final UnaryExpr first;

    public static class Op {
        public final String operator; // "*" or "/"
        public final UnaryExpr expr;

        public Op(String operator, UnaryExpr expr) {
            this.operator = operator;
            this.expr = expr;
        }

        @Override
        public String toString() {
            return " " + operator + " " + expr.toString();
        }
    }

    private final List<Op> ops;

    public MultiplicativeExpr(UnaryExpr first, List<Op> ops) {
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
