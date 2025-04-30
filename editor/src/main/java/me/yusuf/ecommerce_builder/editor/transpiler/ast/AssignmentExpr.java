package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import lombok.Getter;
import lombok.Setter;
import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Expression;

@Getter
@Setter
public class AssignmentExpr implements ExpressionStatement{
    private String left;
    private Expression right;

    @Override
    public String toString() {
        return left + "=" + right.toString();
    }
}
