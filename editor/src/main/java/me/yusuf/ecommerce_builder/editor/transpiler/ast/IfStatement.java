package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Expression;

public class IfStatement implements Statement {
    public Expression condition;
    public Block happyPath;
    public Block sadPath;
}
