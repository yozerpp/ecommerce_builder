package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast;

import lombok.Getter;
import lombok.Setter;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Expression;

@Getter
@Setter
public class IfStatement implements BlockStatement {
    private Expression condition;
    private Block block;
    private Block sadPath;
}
