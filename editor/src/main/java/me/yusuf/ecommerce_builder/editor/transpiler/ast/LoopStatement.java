package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Expression;

public class LoopStatement implements Statement {
    public Expression condition;
    public Block block;
}
