package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast;

import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Primary;

public interface ExpressionStatement extends Statement, Primary {
    String toString();
}
