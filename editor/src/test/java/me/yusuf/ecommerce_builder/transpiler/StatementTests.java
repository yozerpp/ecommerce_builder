package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.Block;
import me.yusuf.ecommerce_builder.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.transpiler.ast.AssignmentExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.FunctionCallExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.ForeachStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.IfStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.LoopStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.VarDeclarationStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Primary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatementTests extends TestBase {

    ASTBuilderVisitor visitor = new ASTBuilderVisitor();

    @Test
    public void testComplexPluginDef() {
        // Create a complex pluginDef instance from the grammar.
        // Grammar for pluginDef: IDENTIFIER (hataExpr | SONRA) block;
        // hataExpr: IDENTIFIER HATA;
        // We will use the hataExpr variant.
        // Our sample input will include a function call inside the block.
        // Input:
        //   MyPlugin MyException hatasında { myFunction(Integer.valueOf(42)); }
        String input = "SATIN_ALMA MyException hatasında yap MyPlugin { myFunction(42); }";
        var parser = getParser(input);
        // Parse the işlevTanımı rule from the parser
        var ctx = parser.işlevTanımı();
        // Visit the parse tree and build the AST PluginDef object.
        PluginDef plugin = visitor.visitIşlevTanımı(ctx);

        // Assert that the pluginDef object is correctly created.
        // Check hookedMethod from the IDENTIFIER before hataExpr.
        Assertions.assertEquals("SATIN_ALMA", plugin.hookedMethod);
        // Check hookedException from the hataExpr (IDENTIFIER in hataExpr).
        Assertions.assertEquals("MyException", plugin.hookedException);
        // Check that the block is not null.
        Assertions.assertNotNull(plugin.block);
        // The block should contain the statements present inside the '{}' braces.
        Block block = plugin.block;
        // For our input, we expect one statement inside the block.
        Assertions.assertEquals(1, block.statements.size());
        Assertions.assertEquals("myFunction(42)", block.statements.get(0).toString());

        // The single statement should be a function call expression.
        // Our visitor constructs ExpressionStatement from function calls.
        // We downcast it to FunctionCallExpr.
        Object stmt = block.statements.get(0);
        Assertions.assertInstanceOf(FunctionCallExpr.class, stmt, "The statement is not an instance of FunctionCallExpr");
        FunctionCallExpr funcCall = (FunctionCallExpr) stmt;
        // Assert that functionName is "myFunction"
        Assertions.assertEquals("myFunction", funcCall.functionName);
        // Assert that the function call has exactly one argument.
        Assertions.assertNotNull(funcCall.args, "Function arguments are null");
        Assertions.assertEquals(1, funcCall.args.length);

        // The argument should be an expression representing the number 42.
        Expression argExpr = funcCall.args[0];
        Primary.Number expectedNumber = new Primary.Number();
        expectedNumber.number = 42;
        Assertions.assertEquals(Primary.wrap(expectedNumber).toString(), argExpr.toString());
    }

    @Test
    public void testLoopStatement() {
        // Test a loop statement: "1 olduğu sürece { }"
        String input = "1 iken { }";
        var parser = getParser(input);
        var ctx = parser.döngüİfadesi();
        LoopStatement actual = visitor.visitDöngüİfadesi(ctx);

        // Expected condition is the expression wrapping number 1.
        var n = new Primary.Number();
        n.number = 1;
        Expr expectedCondition = Primary.wrap(n);
        // Expected block is empty.
        Block expectedBlock = new Block();

        Assertions.assertEquals(expectedCondition.toString(), actual.condition.toString(), "Loop condition does not match the expected expression.");
        Assertions.assertEquals(expectedBlock.statements.size(), actual.block.statements.size(), "Loop block statements size mismatch.");
    }

    @Test
    public void testIfStatementWithoutElse() {
        // Test an if statement without else:
        // "eğer 1 ise { değişken x = 2; }"
        String input = "eğer 1 ise { değişken x = 2; }";
        var parser = getParser(input);
        var ctx = parser.eğerİfadesi();
        IfStatement actual = visitor.visitEğerİfadesi(ctx);

        // Expected condition: number 1 wrapped.
        var n = new Primary.Number();
        n.number = 1;
        Expr expectedCondition = Primary.wrap(n);
        Assertions.assertEquals(expectedCondition.toString(), actual.condition.toString(), "If condition does not match.");

        // Expected happyPath: a block with one var declaration statement.
        Block expectedHappy = new Block();
        VarDeclarationStatement expectedVar = new VarDeclarationStatement();
        expectedVar.varName = "x";
        var num = new Primary.Number();
        num.number = 2;
        expectedVar.value = Primary.wrap(num);
        expectedHappy.statements.add(expectedVar);

        // Check if the happyPath block has one statement.
        Assertions.assertEquals(expectedHappy.statements.size(), actual.happyPath.statements.size(), "Happy path block statement count mismatch.");
        VarDeclarationStatement actualVar = (VarDeclarationStatement) actual.happyPath.statements.get(0);
        Assertions.assertEquals(expectedVar.varName, actualVar.varName, "Variable name mismatch in if-statement.");
        Assertions.assertEquals(expectedVar.value.toString(), actualVar.value.toString(), "Variable assignment mismatch in if-statement.");
        // sadPath should be null.
        Assertions.assertNull(actual.sadPath, "Sad path should be null when not provided.");
    }

    @Test
    public void testIfStatementWithElse() {
        // Test an if statement with else:
        // "eğer 1 ise { değişken x = 2; } değilse { değişken y = 3; }"
        String input = "eğer 1 ise { değişken x = 2; } değilse { değişken y = 3; }";
        var parser = getParser(input);
        var ctx = parser.eğerİfadesi();
        IfStatement actual = visitor.visitEğerİfadesi(ctx);

        // Expected condition: 1
        var n = new Primary.Number();
        n.number = 1;
        Expr expectedCondition = Primary.wrap(n);
        Assertions.assertEquals(expectedCondition.toString(), actual.condition.toString(), "If condition does not match.");

        // Expected happyPath block with var declaration: x = 2
        VarDeclarationStatement expectedHappyVar = new VarDeclarationStatement();
        expectedHappyVar.varName = "x";
        var num2 = new Primary.Number();
        num2.number = 2;
        expectedHappyVar.value = Primary.wrap(num2);

        // Expected sadPath block with var declaration: y = 3
        VarDeclarationStatement expectedSadVar = new VarDeclarationStatement();
        expectedSadVar.varName = "y";
        var num3 = new Primary.Number();
        num3.number = 3;
        expectedSadVar.value = Primary.wrap(num3);

        // Check happyPath block
        Assertions.assertFalse(actual.happyPath.statements.isEmpty(), "Happy path block should not be empty.");
        VarDeclarationStatement actualHappyVar = (VarDeclarationStatement) actual.happyPath.statements.get(0);
        Assertions.assertEquals(expectedHappyVar.varName, actualHappyVar.varName, "Happy path variable name mismatch.");
        Assertions.assertEquals(expectedHappyVar.value.toString(), actualHappyVar.value.toString(), "Happy path assignment mismatch.");

        // Check sadPath block
        Assertions.assertNotNull(actual.sadPath, "Sad path block should not be null.");
        Assertions.assertFalse(actual.sadPath.statements.isEmpty(), "Sad path block should not be empty.");
        VarDeclarationStatement actualSadVar = (VarDeclarationStatement) actual.sadPath.statements.get(0);
        Assertions.assertEquals(expectedSadVar.varName, actualSadVar.varName, "Sad path variable name mismatch.");
        Assertions.assertEquals(expectedSadVar.value.toString(), actualSadVar.value.toString(), "Sad path assignment mismatch.");
    }

    @Test
    public void testForeachStatement() {
        // Test foreach statement: "item içindeki her items için { }"
        String input = "items içindeki her item için { }";
        var parser = getParser(input);
        var ctx = parser.herBiriİfadesi();
        ForeachStatement actual = visitor.visitHerBiriİfadesi(ctx);

        Assertions.assertEquals("item", actual.elementName, "Foreach element name mismatch.");
        Assertions.assertEquals("items", actual.collectionName, "Foreach collection name mismatch.");
    }

    @Test
    public void testVarDeclarationStatement() {
        // Test variable declaration statement: "değişken x = 42;"
        String input = "değişken x = 42;";
        var parser = getParser(input);
        var ctx = parser.değişkenTanımı();
        VarDeclarationStatement actual = visitor.visitDeğişkenTanımı(ctx);

        Assertions.assertEquals("x", actual.varName, "VarDeclaration variable name mismatch.");
        var num = new Primary.Number();
        num.number = 42;
        Assertions.assertEquals(Primary.wrap(num).toString(), actual.value.toString(), "VarDeclaration assignment mismatch.");
    }

    @Test
    public void testFunctionCallStatement() {
        // Test function call statement: "myFunction(1,2);"
        String input = "myFunction(1,2);";
        var parser = getParser(input);
        var ctx = parser.denklemİfadesi();
        Object result = visitor.visitDenklemİfadesi(ctx);
        Assertions.assertInstanceOf(FunctionCallExpr.class, result, "Result is not a FunctionCallExpr.");
        FunctionCallExpr actual = (FunctionCallExpr) result;

        Assertions.assertEquals("myFunction", actual.functionName, "Function call name mismatch.");
        Assertions.assertEquals(2, actual.args.length, "Function call argument count mismatch.");
        var num1 = new Primary.Number();
        num1.number = 1;
        var num2 = new Primary.Number();
        num2.number = 2;
        Assertions.assertEquals(Primary.wrap(num1).toString(), actual.args[0].toString(), "First argument mismatch in function call.");
        Assertions.assertEquals(Primary.wrap(num2).toString(), actual.args[1].toString(), "Second argument mismatch in function call.");
    }

    @Test
    public void testAssignmentStatement() {
        // Test assignment statement: "x = 42;"
        String input = "x = 42;";
        var parser = getParser(input);
        var ctx = parser.atama();
        AssignmentExpr actual = visitor.visitAtama(ctx);

        Assertions.assertEquals("x", actual.left, "Assignment variable name mismatch.");
        var num = new Primary.Number();
        num.number = 42;
        Assertions.assertEquals(Primary.wrap(num).toString(), actual.right.toString(), "Assignment value mismatch.");
    }
}
