package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import lombok.Getter;
import lombok.Setter;
import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Expression;

@Getter
@Setter
public class ReturnStatement implements ASTNode {
    private Expression value;
}
