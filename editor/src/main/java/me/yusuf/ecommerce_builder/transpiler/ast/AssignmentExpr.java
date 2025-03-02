package me.yusuf.ecommerce_builder.transpiler.ast;

import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;

public class AssignmentExpr implements ExpressionStatement{
    public String left;
    public Expression right;
}
