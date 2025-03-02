package me.yusuf.ecommerce_builder.transpiler.ast;

import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expr;

public class IfStatement implements Statement {
    public Expr condition;
    public Block happyPath;
    public Block sadPath;
}
