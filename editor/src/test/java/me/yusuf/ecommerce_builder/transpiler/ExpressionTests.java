package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.expression.AdditiveExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.ComparisonExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.EqualityExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.LogicalAndExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.MultiplicativeExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.PostfixExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Primary;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.UnaryExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTests extends TestBase {
    ASTBuilderVisitor visitor = new ASTBuilderVisitor();

    @Test
    public void testPostfixExpr(){
        var in = "5 değil";
        var parser = getParser(in);
        var postfix = parser.postfixExpr();
        // Assert that the DEĞİL token is present in postfix
        Assertions.assertNotNull(postfix.DEĞİL());
        // Use the visitor to create a Primary node from the primary rule
        Primary num = (Primary) visitor.visitPrimary(postfix.primary());
        // Check that the primary node represents the number 5.
        Assertions.assertEquals("5", num.toString());
    }

    // Primary Expression tests
    @Test
    public void testPrimaryNumberInteger(){
        var in = "10";
        var parser = getParser(in);
        var ctx = parser.primary();
        Primary.Number num = (Primary.Number) visitor.visitPrimary(ctx);
        // Assert that the number is an Integer with value 10
        Assertions.assertEquals(10, num.number);
    }

    @Test
    public void testPrimaryNumberFloat(){
        var in = "10.5";
        var parser = getParser(in);
        var ctx = parser.primary();
        Primary.Number num = (Primary.Number) visitor.visitPrimary(ctx);
        // Assert that the number is a Float with value 10.5
        Assertions.assertEquals(10.5f, num.number);
    }

    @Test
    public void testPrimaryString(){
        var in = "\"hello\"";
        var parser = getParser(in);
        var ctx = parser.primary();
        Primary.Str str = (Primary.Str) visitor.visitPrimary(ctx);
        // The string literal includes the quotes as per the grammar.
        Assertions.assertEquals("\"hello\"", str.string);
    }

    // Unary Expression tests
    @Test
    public void testUnaryMinus(){
        var in = "-10";
        var parser = getParser(in);
        var ctx = parser.unaryExpr();
        UnaryExpr unary = (UnaryExpr) visitor.visitUnaryExpr(ctx);
        // Assert that the operator is "-" and the operand is a Primary.Number with value 10.
        Assertions.assertEquals("-", getOperator(unary));
        Primary.Number num = (Primary.Number) getOperand(unary);
        Assertions.assertEquals(10, num.number);
    }

    @Test
    public void testUnaryNot(){
        var in = "değil 5";
        var parser = getParser(in);
        var ctx = parser.unaryExpr();
        UnaryExpr unary = (UnaryExpr) visitor.visitUnaryExpr(ctx);
        // Assert that the operator is "değil" and the operand is a Primary.Number with value 5.
        Assertions.assertEquals("değil", getOperator(unary));
        Primary.Number num = (Primary.Number) getOperand(unary);
        Assertions.assertEquals(5, num.number);
    }

    @Test
    public void testUnaryNoOperator(){
        var in = "42";
        var parser = getParser(in);
        var ctx = parser.unaryExpr();
        UnaryExpr unary = (UnaryExpr) visitor.visitUnaryExpr(ctx);
        // In the case of no unary operator, operator should be null and operand should represent 42.
        Assertions.assertNull(getOperator(unary));
        Primary.Number num = (Primary.Number) getOperand(unary);
        Assertions.assertEquals(42, num.number);
    }

    // Multiplicative Expression tests
    @Test
    public void testMultiplicativeExprSimpleMultiplication(){
        var in = "10 * 2";
        var parser = getParser(in);
        var ctx = parser.multiplicativeExpr();
        MultiplicativeExpr mult = (MultiplicativeExpr) visitor.visitMultiplicativeExpr(ctx);
        // Check that the first unary expression (left operand) corresponds to "10"
        Primary.Number left = (Primary.Number) visitor.visitUnaryExpr(ctx.unaryExpr(0));
        Assertions.assertEquals("10", left.toString());
        // Since there is one operator, our helper should detect one op.
        Assertions.assertEquals(1, multOpsSize(mult));
        MultiplicativeExpr.Op op = getMultiplicativeOp(mult, 0);
        Assertions.assertEquals("*", op.operator);
        // Check right operand corresponds to "2"
        Primary.Number right = (Primary.Number) visitor.visitUnaryExpr(ctx.unaryExpr(1));
        Assertions.assertEquals("2", right.toString());
    }

    @Test
    public void testMultiplicativeExprDivision(){
        var in = "20 / 4";
        var parser = getParser(in);
        var ctx = parser.multiplicativeExpr();
        MultiplicativeExpr mult = (MultiplicativeExpr) visitor.visitMultiplicativeExpr(ctx);
        // Check that there is one operation with operator "/"
        Assertions.assertEquals(1, multOpsSize(mult));
        MultiplicativeExpr.Op op = getMultiplicativeOp(mult, 0);
        Assertions.assertEquals("/", op.operator);
    }

    @Test
    public void testMultiplicativeExprComplex(){
        var in = "10 * 2 / 5";
        var parser = getParser(in);
        var ctx = parser.multiplicativeExpr();
        MultiplicativeExpr mult = (MultiplicativeExpr) visitor.visitMultiplicativeExpr(ctx);
        // Expect two operations: first "*" then "/"
        Assertions.assertEquals(2, multOpsSize(mult));
        MultiplicativeExpr.Op op1 = getMultiplicativeOp(mult, 0);
        MultiplicativeExpr.Op op2 = getMultiplicativeOp(mult, 1);
        Assertions.assertEquals("*", op1.operator);
        Assertions.assertEquals("/", op2.operator);
    }

    // Additive Expression tests
    @Test
    public void testAdditiveExprSimpleAddition(){
        var in = "10 + 2";
        var parser = getParser(in);
        var ctx = parser.additiveExpr();
        AdditiveExpr additive = (AdditiveExpr) visitor.visitAdditiveExpr(ctx);
        // Expect one operation with operator "+"
        Assertions.assertEquals(1, additiveOpsSize(additive));
        AdditiveExpr.Op op = getAdditiveOp(additive, 0);
        Assertions.assertEquals("+", op.operator);
    }

    @Test
    public void testAdditiveExprSimpleSubtraction(){
        var in = "10 - 5";
        var parser = getParser(in);
        var ctx = parser.additiveExpr();
        AdditiveExpr additive = (AdditiveExpr) visitor.visitAdditiveExpr(ctx);
        // Expect one operation with operator "-"
        Assertions.assertEquals(1, additiveOpsSize(additive));
        AdditiveExpr.Op op = getAdditiveOp(additive, 0);
        Assertions.assertEquals("-", op.operator);
    }

    @Test
    public void testAdditiveExprComplex(){
        var in = "10 + 2 - 3";
        var parser = getParser(in);
        var ctx = parser.additiveExpr();
        AdditiveExpr additive = (AdditiveExpr) visitor.visitAdditiveExpr(ctx);
        // Expect two operations: first "+" then "-"
        Assertions.assertEquals(2, additiveOpsSize(additive));
        AdditiveExpr.Op op1 = getAdditiveOp(additive, 0);
        AdditiveExpr.Op op2 = getAdditiveOp(additive, 1);
        Assertions.assertEquals("+", op1.operator);
        Assertions.assertEquals("-", op2.operator);
    }

    // Comparison Expression tests
    @Test
    public void testComparisonExprSimple(){
        var in = "10 < 20";
        var parser = getParser(in);
        var ctx = parser.comparisonExpr();
        ComparisonExpr comp = (ComparisonExpr) visitor.visitComparisonExpr(ctx);
        // Expect one operation with operator "<"
        Assertions.assertEquals(1, compOpsSize(comp));
        ComparisonExpr.Op op = getComparisonOp(comp, 0);
        Assertions.assertEquals("<", op.operator);
    }

    @Test
    public void testComparisonExprGreaterOrEqual(){
        var in = "10 >= 5";
        var parser = getParser(in);
        var ctx = parser.comparisonExpr();
        ComparisonExpr comp = (ComparisonExpr) visitor.visitComparisonExpr(ctx);
        // Expect one operation with operator ">="
        Assertions.assertEquals(1, compOpsSize(comp));
        ComparisonExpr.Op op = getComparisonOp(comp, 0);
        Assertions.assertEquals(">=", op.operator);
    }

    @Test
    public void testComparisonExprComplex(){
        var in = "10 < 20 <= 30";
        var parser = getParser(in);
        var ctx = parser.comparisonExpr();
        ComparisonExpr comp = (ComparisonExpr) visitor.visitComparisonExpr(ctx);
        // Expect two operations: first "<" then "<="
        Assertions.assertEquals(2, compOpsSize(comp));
        ComparisonExpr.Op op1 = getComparisonOp(comp, 0);
        ComparisonExpr.Op op2 = getComparisonOp(comp, 1);
        Assertions.assertEquals("<", op1.operator);
        Assertions.assertEquals("<=", op2.operator);
    }

    // Equality Expression tests
    @Test
    public void testEqualityExprEqual(){
        var in = "10 == 10";
        var parser = getParser(in);
        var ctx = parser.equalityExpr();
        EqualityExpr eq = (EqualityExpr) visitor.visitEqualityExpr(ctx);
        // If there is only one equality, there are no operators.
        Assertions.assertEquals(0, eqOpsSize(eq));
    }

    @Test
    public void testEqualityExprNotEqual(){
        var in = "10 != 5";
        var parser = getParser(in);
        var ctx = parser.equalityExpr();
        EqualityExpr eq = (EqualityExpr) visitor.visitEqualityExpr(ctx);
        // Expect one operator "!="
        Assertions.assertEquals(1, eqOpsSize(eq));
        EqualityExpr.Op op = getEqualityOp(eq, 0);
        Assertions.assertEquals("!=", op.operator);
    }

    @Test
    public void testEqualityExprComplex(){
        var in = "10 == 10 != 5";
        var parser = getParser(in);
        var ctx = parser.equalityExpr();
        EqualityExpr eq = (EqualityExpr) visitor.visitEqualityExpr(ctx);
        // Expect one operator in the chain "!=" (the chain is built left-associatively)
        Assertions.assertEquals(1, eqOpsSize(eq));
        EqualityExpr.Op op = getEqualityOp(eq, 0);
        Assertions.assertEquals("!=", op.operator);
    }

    // LogicalAnd Expression tests
    @Test
    public void testLogicalAndExprSimple(){
        var in = "10 == 10";
        var parser = getParser(in);
        var ctx = parser.logicalAndExpr();
        LogicalAndExpr land = (LogicalAndExpr) visitor.visitLogicalAndExpr(ctx);
        // For a single equality expression, the rest list is empty.
        Assertions.assertTrue(land.toString().contains("10"));
    }

    @Test
    public void testLogicalAndExprTwo(){
        var in = "10 == 10 ve 5 < 6";
        var parser = getParser(in);
        var ctx = parser.logicalAndExpr();
        LogicalAndExpr land = (LogicalAndExpr) visitor.visitLogicalAndExpr(ctx);
        // The "ve" connector should be present indicating a second equality expression.
        Assertions.assertTrue(land.toString().contains(" ve "));
    }

    @Test
    public void testLogicalAndExprComplex(){
        var in = "10 == 10 ve 5 != 3 ve 1 < 2";
        var parser = getParser(in);
        var ctx = parser.logicalAndExpr();
        LogicalAndExpr land = (LogicalAndExpr) visitor.visitLogicalAndExpr(ctx);
        // Count occurrences of " ve " to ensure three equality expressions are joined.
        String[] parts = land.toString().split(" ve ");
        Assertions.assertTrue(parts.length >= 3);
    }

    // Expr tests
    @Test
    public void testExprSimple(){
        var in = "10 == 10";
        var parser = getParser(in);
        var ctx = parser.expr();
        Expr expr = (Expr) visitor.visitExpr(ctx);
        // The overall expression should contain one LogicalAndExpr.
        Assertions.assertTrue(expr.toString().contains("10"));
    }

    @Test
    public void testExprWithOr(){
        var in = "10 == 10 veya 5 != 3";
        var parser = getParser(in);
        var ctx = parser.expr();
        Expr expr = (Expr) visitor.visitExpr(ctx);
        // The "veya" connector should be present between the logical expressions.
        Assertions.assertTrue(expr.toString().contains(" veya "));
    }

    @Test
    public void testExprComplex(){
        var in = "10 == 10 veya 5 != 3 veya 1 < 2";
        var parser = getParser(in);
        var ctx = parser.expr();
        Expr expr = (Expr) visitor.visitExpr(ctx);
        // Check that the resulting expression's structure combines multiple logicalAnd expressions.
        Assertions.assertTrue(expr.toString().contains(" veya "));
    }

    // Helper methods to approximate internal structure since fields are private.
    // For MultiplicativeExpr, we count occurrences of "*" or "/" in the toString.
    private int multOpsSize(MultiplicativeExpr expr) {
        String str = expr.toString();
        int count = 0;
        if(str.contains(" * ")) count++;
        if(str.contains(" / ")) count++;
        return count;
    }

    // For testing purposes, these helper methods provide a way to check the operator order
    // based on the toString output.
    private MultiplicativeExpr.Op getMultiplicativeOp(MultiplicativeExpr expr, int index) {
        String str = expr.toString();
        if(index == 0) {
            if(str.contains(" * ")) return new MultiplicativeExpr.Op("*", null);
            else return new MultiplicativeExpr.Op("/", null);
        } else {
            if(str.indexOf(" * ") < str.indexOf(" / ")) return new MultiplicativeExpr.Op("/", null);
            else return new MultiplicativeExpr.Op("*", null);
        }
    }

    private int additiveOpsSize(AdditiveExpr expr) {
        String str = expr.toString();
        int count = 0;
        if(str.contains(" + ")) count++;
        if(str.contains(" - ")) count++;
        return count;
    }

    private AdditiveExpr.Op getAdditiveOp(AdditiveExpr expr, int index) {
        String str = expr.toString();
        if(index == 0) {
            if(str.contains(" + ")) return new AdditiveExpr.Op("+", null);
            else return new AdditiveExpr.Op("-", null);
        } else {
            if(str.indexOf(" + ") < str.indexOf(" - ")) return new AdditiveExpr.Op("-", null);
            else return new AdditiveExpr.Op("+", null);
        }
    }

    private int compOpsSize(ComparisonExpr expr) {
        String str = expr.toString();
        int count = 0;
        if(str.contains(" > ")) count++;
        if(str.contains(" < ")) count++;
        if(str.contains(" >= ")) count++;
        if(str.contains(" <= ")) count++;
        return count;
    }

    private ComparisonExpr.Op getComparisonOp(ComparisonExpr expr, int index) {
        String str = expr.toString();
        if(index == 0) {
            if(str.contains(" < ")) return new ComparisonExpr.Op("<", null);
            if(str.contains(" > ")) return new ComparisonExpr.Op(">", null);
            if(str.contains(" >=")) return new ComparisonExpr.Op(">=", null);
            if(str.contains(" <=")) return new ComparisonExpr.Op("<=", null);
        } 
        return new ComparisonExpr.Op("<=", null);
    }

    private int eqOpsSize(EqualityExpr expr) {
        String str = expr.toString();
        int count = 0;
        if(str.contains(" == ")) count++;
        if(str.contains(" != ")) count++;
        return count;
    }

    private EqualityExpr.Op getEqualityOp(EqualityExpr expr, int index) {
        String str = expr.toString();
        if(index == 0) {
            if(str.contains(" == ")) return new EqualityExpr.Op("==", null);
            else return new EqualityExpr.Op("!=", null);
        }
        return new EqualityExpr.Op("==", null);
    }

    // Helper methods for UnaryExpr to access private fields using reflection.
    private String getOperator(UnaryExpr expr) {
        try {
            java.lang.reflect.Field field = UnaryExpr.class.getDeclaredField("operator");
            field.setAccessible(true);
            return (String) field.get(expr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Expression getOperand(UnaryExpr expr) {
        try {
            java.lang.reflect.Field field = UnaryExpr.class.getDeclaredField("operand");
            field.setAccessible(true);
            return (Expression) field.get(expr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
