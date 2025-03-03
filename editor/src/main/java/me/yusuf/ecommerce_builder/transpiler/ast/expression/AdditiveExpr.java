package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdditiveExpr implements Expression {
    private final MultiplicativeExpr first;

    public static class Op {
        public final String operator; // "+" or "-"
        public final MultiplicativeExpr expr;

        public Op(String operator, MultiplicativeExpr expr) {
            this.operator = operator;
            this.expr = expr;
        }

        @Override
        public String toString() {
            return " " + operator + " " + expr.toString();
        }
    }

    public final List<Op> ops = new ArrayList<>();

    public AdditiveExpr(MultiplicativeExpr first, List<Op> ops) {
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
