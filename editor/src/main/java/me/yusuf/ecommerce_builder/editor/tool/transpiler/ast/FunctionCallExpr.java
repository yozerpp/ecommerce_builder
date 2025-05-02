package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast;

import lombok.Getter;
import lombok.Setter;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Expression;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
public class FunctionCallExpr implements ExpressionStatement {
    private String functionName;
    private Expression[] args;

    @Override
    public String toString() {
        return functionName + "(" + Arrays.stream(args).map(Object::toString).collect(Collectors.joining(",")) + ")";
    }
}
