package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Expression;

public class AssignmentExpr implements ExpressionStatement{
    public String left;
    public Expression right;

    @Override
    public String toString() {
        return left + "=" + right.toString();
    }
}
