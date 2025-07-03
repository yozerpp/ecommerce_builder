package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast;

import lombok.Getter;
import lombok.Setter;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Expression;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Primary;

@Getter
@Setter
public class AssignmentExpr implements ExpressionStatement{
    private Primary.Identifier left;
    private Expression right;

    @Override
    public String toString() {
        return left + "=" + right.toString();
    }
}
