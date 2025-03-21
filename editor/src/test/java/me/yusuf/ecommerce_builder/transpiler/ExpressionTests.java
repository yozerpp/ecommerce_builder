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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTests extends TestBase {
    ASTBuilderVisitor visitor = new ASTBuilderVisitor();

    @Test
    public void testPostfixExpr(){
        var in = "5 değil";
        var parser = getParser(in);
        var postfix = parser.değilİfadesi();
        // Assert that the DEĞİL token is present in postfix context
        Assertions.assertNotNull(postfix.DEĞİL());
        // Use the visitor to create a Primary node from the primary rule
        Primary num = (Primary) visitor.visitDeğer(postfix.değer());
        // Check that the primary node represents the number 5 using direct field access.
        // In our Primary.Number class, the field 'number' should equal 5.
        Primary.Number numberNode = (Primary.Number) num;
        // Compare as numbers. We assume that if the literal is "5", it is parsed as Integer 5.
        Assertions.assertEquals(5, numberNode.number);
    }

    // Primary Expression tests
    @Test
    public void testPrimaryNumberInteger(){
        var in = "10";
        var parser = getParser(in);
        var ctx = parser.değer();
        Primary.Number num = (Primary.Number) visitor.visitDeğer(ctx);
        // Assert that the number is an Integer with value 10 using direct field access.
        Assertions.assertEquals(10, num.number);
    }

    @Test
    public void testPrimaryNumberFloat(){
        var in = "10.5";
        var parser = getParser(in);
        var ctx = parser.değer();
        Primary.Number num = (Primary.Number) visitor.visitDeğer(ctx);
        // Assert that the number is a Float with value 10.5 using direct field access.
        Assertions.assertEquals(10.5f, num.number);
    }

    @Test
    public void testPrimaryString(){
        var in = "\"hello\"";
        var parser = getParser(in);
        var ctx = parser.değer();
        Primary.Str str = (Primary.Str) visitor.visitDeğer(ctx);
        // The string literal includes the quotes as per the grammar.
        Assertions.assertEquals("\"hello\"", str.string);
    }

    // Unary Expression tests
    @Test
    public void testUnaryMinus(){
        var in = "-10";
        var parser = getParser(in);
        var ctx = parser.tekliDenklem();
        UnaryExpr unary = (UnaryExpr) visitor.visitTekliDenklem(ctx);
        // Assert that the operator is "-" using direct field access.
        Assertions.assertEquals("-", unary.operator);
        var num = unary.operand;
        Assertions.assertEquals("10", num.toString());
    }

    @Test
    public void testUnaryNot(){
        var in = "değil 5";
        var parser = getParser(in);
        var ctx = parser.tekliDenklem();
        UnaryExpr unary = (UnaryExpr) visitor.visitTekliDenklem(ctx);
        // Assert that the operator is "değil" using direct field access.
        Assertions.assertEquals("değil", unary.operator);
        var num = unary.operand;
        Assertions.assertEquals("5", num.toString());
    }

    @Test
    public void testUnaryNoOperator(){
        var in = "42";
        var parser = getParser(in);
        var ctx = parser.tekliDenklem();
        UnaryExpr unary = (UnaryExpr) visitor.visitTekliDenklem(ctx);
        // When there is no unary operator, operator should be null and operand should represent 42.
        Assertions.assertNull(unary.operator);
        var num = unary.operand;
        Assertions.assertEquals("42", num.toString());
    }

    // Multiplicative Expression tests
    @Test
    public void testMultiplicativeExprSimpleMultiplication(){
        var in = "10 * 2";
        var parser = getParser(in);
        var ctx = parser.çarpmaDenklemi();
        MultiplicativeExpr mult = (MultiplicativeExpr) visitor.visitÇarpmaDenklemi(ctx);
        // Use direct field access to check the left operand from the first UnaryExpr.
        UnaryExpr leftUnary = mult.first;
        PostfixExpr left = (PostfixExpr) leftUnary.operand;
        Assertions.assertEquals("10", left.toString());
        // Check that there is one operator with value "*" using direct field access.
        Assertions.assertEquals(1, mult.ops.size());
        Assertions.assertEquals("*", mult.ops.get(0).operator);
        // Use direct field access to check the right operand of the operator.
        UnaryExpr rightUnary = (UnaryExpr) visitor.visitTekliDenklem(ctx.tekliDenklem(1));
        Assertions.assertEquals("2", rightUnary.toString());
    }

    @Test
    public void testMultiplicativeExprDivision(){
        var in = "20 / 4";
        var parser = getParser(in);
        var ctx = parser.çarpmaDenklemi();
        MultiplicativeExpr mult = (MultiplicativeExpr) visitor.visitÇarpmaDenklemi(ctx);
        // Check that there is one operation with operator "/" using direct field access.
        Assertions.assertEquals(1, mult.ops.size());
        Assertions.assertEquals("/", mult.ops.get(0).operator);
    }

    @Test
    public void testMultiplicativeExprComplex(){
        var in = "10 * 2 / 5";
        var parser = getParser(in);
        var ctx = parser.çarpmaDenklemi();
        MultiplicativeExpr mult = (MultiplicativeExpr) visitor.visitÇarpmaDenklemi(ctx);
        // Expect two operations: first "*" then "/" using direct field access.
        Assertions.assertEquals(2, mult.ops.size());
        Assertions.assertEquals("*", mult.ops.get(0).operator);
        Assertions.assertEquals("/", mult.ops.get(1).operator);
    }

    // Additive Expression tests
    @Test
    public void testAdditiveExprSimpleAddition(){
        var in = "10 + 2";
        var parser = getParser(in);
        var ctx = parser.toplamaDenklemi();
        AdditiveExpr additive = (AdditiveExpr) visitor.visitToplamaDenklemi(ctx);
        // Expect one operation with operator "+" using direct field access.
        Assertions.assertEquals(1, additive.ops.size());
        Assertions.assertEquals("+", additive.ops.get(0).operator);
    }

    @Test
    public void testAdditiveExprSimpleSubtraction(){
        var in = "10 - 5";
        var parser = getParser(in);
        var ctx = parser.toplamaDenklemi();
        AdditiveExpr additive = (AdditiveExpr) visitor.visitToplamaDenklemi(ctx);
        // Expect one operation with operator "-" using direct field access.
        Assertions.assertEquals(1, additive.ops.size());
        Assertions.assertEquals("-", additive.ops.get(0).operator);
    }

    @Test
    public void testAdditiveExprComplex(){
        var in = "10 + 2 - 3";
        var parser = getParser(in);
        var ctx = parser.toplamaDenklemi();
        AdditiveExpr additive = (AdditiveExpr) visitor.visitToplamaDenklemi(ctx);
        // Expect two operations: first "+" then "-" using direct field access.
        Assertions.assertEquals(2, additive.ops.size());
        Assertions.assertEquals("+", additive.ops.get(0).operator);
        Assertions.assertEquals("-", additive.ops.get(1).operator);
    }

    // Comparison Expression tests
    @Test
    public void testComparisonExprSimple(){
        var in = "10 < 20";
        var parser = getParser(in);
        var ctx = parser.karşılaştırmaDenklemi();
        ComparisonExpr comp = (ComparisonExpr) visitor.visitKarşılaştırmaDenklemi(ctx);
        // Expect one operation with operator "<" using direct field access.
        Assertions.assertEquals(1, comp.ops.size());
        Assertions.assertEquals("<", comp.ops.get(0).operator);
    }

    @Test
    public void testComparisonExprGreaterOrEqual(){
        var in = "10 >= 5";
        var parser = getParser(in);
        var ctx = parser.karşılaştırmaDenklemi();
        ComparisonExpr comp = (ComparisonExpr) visitor.visitKarşılaştırmaDenklemi(ctx);
        // Expect one operation with operator ">=" using direct field access.
        Assertions.assertEquals(1, comp.ops.size());
        Assertions.assertEquals(">=", comp.ops.get(0).operator);
    }

    @Test
    public void testComparisonExprComplex(){
        var in = "10 < 20 <= 30";
        var parser = getParser(in);
        var ctx = parser.karşılaştırmaDenklemi();
        ComparisonExpr comp = (ComparisonExpr) visitor.visitKarşılaştırmaDenklemi(ctx);
        // Expect two operations: first "<" then "<=" using direct field access.
        Assertions.assertEquals(2, comp.ops.size());
        Assertions.assertEquals("<", comp.ops.get(0).operator);
        Assertions.assertEquals("<=", comp.ops.get(1).operator);
    }

    // Equality Expression tests
    @Test
    public void testEqualityExprEqual(){
        var in = "10 == 10";
        var parser = getParser(in);
        var ctx = parser.eşitlikDenklemi();
        EqualityExpr eq = (EqualityExpr) visitor.visitEşitlikDenklemi(ctx);
        // For a single equality expression, there are no operators.
        Assertions.assertEquals(1, eq.ops.size());
        Assertions.assertEquals("==", eq.ops.get(0).operator);
        Assertions.assertEquals("10", eq.first.toString());
        Assertions.assertEquals("10", eq.ops.get(0).expr.first.toString());
    }

    @Test
    public void testEqualityExprNotEqual(){
        var in = "10 != 5";
        var parser = getParser(in);
        var ctx = parser.eşitlikDenklemi();
        EqualityExpr eq = (EqualityExpr) visitor.visitEşitlikDenklemi(ctx);
        // Expect one operator "!=" using direct field access.
        Assertions.assertEquals(1, eq.ops.size());
        Assertions.assertEquals("!=", eq.ops.get(0).operator);
        Assertions.assertEquals("10", eq.first.toString());
        Assertions.assertEquals("5", eq.ops.get(0).expr.first.toString());
    }

    @Test
    public void testEqualityExprComplex(){
        var in = "10 == 10 != 5";
        var parser = getParser(in);
        var ctx = parser.eşitlikDenklemi();
        EqualityExpr eq = (EqualityExpr) visitor.visitEşitlikDenklemi(ctx);
        // Expect one operator in the chain "!=" using direct field access.
        Assertions.assertEquals(2, eq.ops.size());
        Assertions.assertEquals("==", eq.ops.get(0).operator);
        Assertions.assertEquals("10", eq.first.toString());
        Assertions.assertEquals("10", eq.ops.get(0).expr.first.toString());
        Assertions.assertEquals("!=", eq.ops.get(1).operator);
        Assertions.assertEquals("5", eq.ops.get(1).expr.first.toString());
    }

    // LogicalAnd Expression tests
    @Test
    public void testLogicalAndExprSimple(){
        var in = "10 == 10";
        var parser = getParser(in);
        var ctx = parser.mantıksalVeDenklemi();
        LogicalAndExpr land = (LogicalAndExpr) visitor.visitMantıksalVeDenklemi(ctx);
        // For a single equality expression, check that first exists and the rest list is empty.
        Assertions.assertNotNull(land.first);
        Assertions.assertEquals("10 == 10", land.first.toString()); //better than nothing.
        Assertions.assertEquals(0, land.rest.size());
    }

    @Test
    public void testLogicalAndExprTwo(){
        var in = "10 == 10 ve 5 < 6";
        var parser = getParser(in);
        var ctx = parser.mantıksalVeDenklemi();
        LogicalAndExpr land = (LogicalAndExpr) visitor.visitMantıksalVeDenklemi(ctx);
        // There should be one additional equality expression in the rest list.
        Assertions.assertEquals("10 == 10", land.first.toString());
        Assertions.assertEquals(1, land.rest.size());
        Assertions.assertEquals("5 < 6", land.rest.getFirst().toString());
    }

    @Test
    public void testLogicalAndExprComplex(){
        var in = "10 == 10 ve 5 != 3 ve 1 < 2";
        var parser = getParser(in);
        var ctx = parser.mantıksalVeDenklemi();
        LogicalAndExpr land = (LogicalAndExpr) visitor.visitMantıksalVeDenklemi(ctx);
        // Expect two additional equality expressions in the rest list.
        Assertions.assertEquals("10 == 10", land.first.toString());
        Assertions.assertEquals(2, land.rest.size());
        Assertions.assertEquals("5 != 3", land.rest.getFirst().toString());
        Assertions.assertEquals("1 < 2", land.rest.getLast().toString());
    }

    // Expr tests
    @Test
    public void testExprSimple(){
        var in = "10 == 10";
        var parser = getParser(in);
        var ctx = parser.denklem();
        Expr expr = (Expr) visitor.visitDenklem(ctx);
        // The overall expression should have a non-null first logicalAnd expression and no additional expressions.
        Assertions.assertNotNull(expr.first);
        Assertions.assertEquals(0, expr.rest.size());
    }

    @Test
    public void testExprWithOr(){
        var in = "10 == 10 veya 5 != 3";
        var parser = getParser(in);
        var ctx = parser.denklem();
        Expr expr = (Expr) visitor.visitDenklem(ctx);
        // There should be one additional logicalAnd expression (from the "veya" connector).
        Assertions.assertEquals(1, expr.rest.size());
    }

    @Test
    public void testExprComplex(){
        var in = "10 == 10 veya 5 != 3 veya 1 < 2";
        var parser = getParser(in);
        var ctx = parser.denklem();
        Expr expr = (Expr) visitor.visitDenklem(ctx);
        // Check that there are two additional logicalAnd expressions.
        Assertions.assertEquals(2, expr.rest.size());
    }
}
