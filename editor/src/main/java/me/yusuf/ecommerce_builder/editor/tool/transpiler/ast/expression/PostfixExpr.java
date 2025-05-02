package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression;

/**
 * @param hasNot true if the optional DEĞİL token is present
 */
public record PostfixExpr(Expression primary, boolean hasNot)  implements Expression{

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        PostfixExpr that = (PostfixExpr) object;
        return hasNot == that.hasNot && primary.equals(that.primary);
    }

    @Override
    public int hashCode() {
        int result = primary.hashCode();
        result = 31 * result + Boolean.hashCode(hasNot);
        return result;
    }

    @Override
    public String toString() {
        return primary.toString() + (hasNot ? " değil" : "");
    }
}
