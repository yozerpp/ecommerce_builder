package me.yusuf.ecommerce_builder.transpiler.ast;

import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;

public class LoopStatement implements Statement {
    public Expression condition;
    public Block block;
}
