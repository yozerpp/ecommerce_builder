package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Expression;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FunctionCallExpr implements ExpressionStatement{
    public String functionName;
    public Expression[] args;

    @Override
    public String toString() {
        return functionName + "(" + Arrays.stream(args).map(Object::toString).collect(Collectors.joining(",")) + ")";
    }
}
