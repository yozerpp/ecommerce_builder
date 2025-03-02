package me.yusuf.ecommerce_builder.transpiler.ast;

import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;

public class FunctionCallExpr implements ExpressionStatement{
    public String functionName;
    public Expression[] args;
}
