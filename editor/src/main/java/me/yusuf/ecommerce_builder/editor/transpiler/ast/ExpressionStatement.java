package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.Primary;

public interface ExpressionStatement extends Statement, Primary {
    String toString();
}
