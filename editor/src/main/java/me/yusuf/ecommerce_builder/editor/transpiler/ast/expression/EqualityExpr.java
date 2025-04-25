package me.yusuf.ecommerce_builder.editor.transpiler.ast.expression;

import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        EqualityExpr that = (EqualityExpr) object;
        return first.equals(that.first) && Objects.equals(ops, that.ops);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + Objects.hashCode(ops);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        if (ops!=null)for (Op op : ops) {
            sb.append(op.toString());
        }
        return sb.toString();
    }
}
