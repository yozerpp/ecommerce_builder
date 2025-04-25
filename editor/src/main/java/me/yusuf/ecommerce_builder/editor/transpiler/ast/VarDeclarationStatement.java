package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Expression;

public class VarDeclarationStatement implements Statement{
    public String varName;
    public Expression value;
}
