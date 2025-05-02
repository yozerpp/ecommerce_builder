package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression;

import java.util.List;
import java.util.Objects;

public record EqualityExpr(ComparisonExpr first, List<Op> ops) implements Expression {
    /**
     * @param operator either "==" or "!="
     */
    public record Op(String operator, ComparisonExpr expr) {

        @Override
            public String toString() {
                return " " + operator + " " + expr.toString();
            }
        }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        EqualityExpr that = (EqualityExpr) object;
        return first.equals(that.first) && Objects.equals(ops, that.ops);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        if (ops != null) for (Op op : ops) {
            sb.append(op.toString());
        }
        return sb.toString();
    }
}
