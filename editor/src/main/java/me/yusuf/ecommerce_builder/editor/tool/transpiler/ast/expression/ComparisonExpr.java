package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression;

import java.util.List;
import java.util.Objects;

public record ComparisonExpr(AdditiveExpr first, List<Op> ops) implements Expression {
    /**
     * @param operator one of ">", "<", ">=", "<="
     */
    public record Op(String operator, AdditiveExpr expr) {

        @Override
            public String toString() {
                return " " + operator + " " + expr.toString();
            }
        }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        ComparisonExpr that = (ComparisonExpr) object;
        return first.equals(that.first) && Objects.equals(ops, that.ops);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString());
        if (ops != null)
            for (Op op : ops) {
                sb.append(op.toString());
            }
        return sb.toString();
    }
}
