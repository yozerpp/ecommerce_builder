package me.yusuf.ecommerce_builder.transpiler.ast;

import me.yusuf.ecommerce_builder.transpiler.ast.expression.Primary;

public interface ExpressionStatement extends Statement, Primary {
    String toString();
}
