package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import java.util.ArrayList;
import java.util.List;

public class MultiplicativeExpr implements Expression {
    public final UnaryExpr first;

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

    public final List<Op> ops = new ArrayList<>();

    public MultiplicativeExpr(UnaryExpr first, List<Op> ops) {
        this.first = first;
        this.ops.addAll(ops);
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
