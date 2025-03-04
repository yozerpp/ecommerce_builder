package me.yusuf.ecommerce_builder.transpiler.ast.expression;

import me.yusuf.ecommerce_builder.transpiler.ast.ASTNode;


public interface Expression extends ASTNode {
    public String toString();
    public boolean equals(Object expression);
    public int hashCode();
}
