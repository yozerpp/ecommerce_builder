package me.yusuf.ecommerce_builder.transpiler.ast;

// Represents binary operations, such as +, -, '*', '/', relational and logical operators.
public class BinaryExpr implements Expression {
    public final Expression left;
    public final Expression right;
    public final String operator;

    public BinaryExpr(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
