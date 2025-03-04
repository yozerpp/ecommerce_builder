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
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;
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
        TurkishPseudoCodeParser parser = getParser(input);
        // Parse the pluginDef rule from the parser
        TurkishPseudoCodeParser.PluginDefContext ctx = parser.pluginDef();
        // Visit the parse tree and build the AST PluginDef object.
        PluginDef plugin = visitor.visitPluginDef(ctx);

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
        Assertions.assertEquals("myFunction(42)", block.statements.get(Integer.valueOf(0)).toString());

        // The single statement should be a function call expression.
        // Our visitor constructs ExpressionStatement from function calls.
        // We downcast it to FunctionCallExpr.
        Object stmt = block.statements.get(Integer.valueOf(0));
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
        Assertions.assertEquals(Primary.wrap(expectedNumber), argExpr);
    }

    @Test
    public void testLoopStatement() {
        // Test a loop statement: "1 olduğu sürece { }"
        String input = "1 iken { }";
        TurkishPseudoCodeParser parser = getParser(input);
        TurkishPseudoCodeParser.LoopStatementContext ctx = parser.loopStatement();
        LoopStatement actual = visitor.visitLoopStatement(ctx);

        // Expected condition is the expression wrapping number 1.
        var n  =new Primary.Number();
        n.number = 1;
        Expr expectedCondition = Primary.wrap(n);
        // Expected block is empty.
        Block expectedBlock = new Block();

        Assertions.assertEquals(expectedCondition, actual.condition, "Loop condition does not match the expected expression.");
        Assertions.assertEquals(expectedBlock.statements.size(), actual.block.statements.size(), "Loop block statements size mismatch.");
    }

    @Test
    public void testIfStatementWithoutElse() {
        // Test an if statement without else:
        // "eğer 1 ise { değişken x = 2; }"
        String input = "eğer 1 ise { değişken x = 2; }";
        TurkishPseudoCodeParser parser = getParser(input);
        TurkishPseudoCodeParser.IfStatementContext ctx = parser.ifStatement();
        IfStatement actual = visitor.visitIfStatement(ctx);

        // Expected condition: number 1 wrapped.
        var n = new Primary.Number();
        n.number =1;
        Expr expectedCondition = Primary.wrap(n);
        Assertions.assertEquals(expectedCondition, actual.condition, "If condition does not match.");

        // Expected happyPath: a block with one var declaration statement.
        Block expectedHappy = new Block();
        VarDeclarationStatement expectedVar = new VarDeclarationStatement();
        AssignmentExpr expectedAssign = new AssignmentExpr();
        expectedAssign.left = "x";
        expectedAssign.right = Primary.wrap(new Primary.Number(Integer.valueOf(2)));
        expectedVar.expr = expectedAssign;
        expectedHappy.statements.add(expectedVar);

        // Check if the happyPath block has one statement.
        Assertions.assertEquals(expectedHappy.statements.size(), actual.happyPath.statements.size(), "Happy path block statement count mismatch.");
        VarDeclarationStatement actualVar = (VarDeclarationStatement) actual.happyPath.statements.get(Integer.valueOf(0));
        Assertions.assertEquals(expectedVar.expr.left, actualVar.expr.left, "Variable name mismatch in if-statement.");
        Assertions.assertEquals(expectedVar.expr.right, actualVar.expr.right, "Variable assignment mismatch in if-statement.");
        // sadPath should be null.
        Assertions.assertNull(actual.sadPath, "Sad path should be null when not provided.");
    }

    @Test
    public void testIfStatementWithElse() {
        // Test an if statement with else:
        // "eğer 1 ise { değişken x = 2; } değilse { değişken y = 3; }"
        String input = "eğer 1 ise { değişken x = 2; } değilse { değişken y = 3; }";
        TurkishPseudoCodeParser parser = getParser(input);
        TurkishPseudoCodeParser.IfStatementContext ctx = parser.ifStatement();
        IfStatement actual = visitor.visitIfStatement(ctx);

        // Expected condition: 1
        var n=  new Primary.Number();
        n.number = 1;
        Expr expectedCondition = Primary.wrap(n);
        Assertions.assertEquals(expectedCondition, actual.condition, "If condition does not match.");

        // Expected happyPath block with var declaration: x = 2
        VarDeclarationStatement expectedHappyVar = new VarDeclarationStatement();
        AssignmentExpr happyAssign = new AssignmentExpr();
        happyAssign.left = "x";
        happyAssign.right = Primary.wrap(new Primary.Number(Integer.valueOf(2)));
        expectedHappyVar.expr = happyAssign;

        // Expected sadPath block with var declaration: y = 3
        VarDeclarationStatement expectedSadVar = new VarDeclarationStatement();
        AssignmentExpr sadAssign = new AssignmentExpr();
        sadAssign.left = "y";
        sadAssign.right = Primary.wrap(new Primary.Number(Integer.valueOf(3)));
        expectedSadVar.expr = sadAssign;

        // Check happyPath block
        Assertions.assertFalse(actual.happyPath.statements.isEmpty(), "Happy path block should not be empty.");
        VarDeclarationStatement actualHappyVar = (VarDeclarationStatement) actual.happyPath.statements.get(Integer.valueOf(0));
        Assertions.assertEquals(expectedHappyVar.expr.left, actualHappyVar.expr.left, "Happy path variable name mismatch.");
        Assertions.assertEquals(expectedHappyVar.expr.right, actualHappyVar.expr.right, "Happy path assignment mismatch.");

        // Check sadPath block
        Assertions.assertNotNull(actual.sadPath, "Sad path block should not be null.");
        Assertions.assertFalse(actual.sadPath.statements.isEmpty(), "Sad path block should not be empty.");
        VarDeclarationStatement actualSadVar = (VarDeclarationStatement) actual.sadPath.statements.get(Integer.valueOf(0));
        Assertions.assertEquals(expectedSadVar.expr.left, actualSadVar.expr.left, "Sad path variable name mismatch.");
        Assertions.assertEquals(expectedSadVar.expr.right, actualSadVar.expr.right, "Sad path assignment mismatch.");
    }

    @Test
    public void testForeachStatement() {
        // Test foreach statement: "item içindeki her items için { }"
        String input = "items içindeki her item için { }";
        TurkishPseudoCodeParser parser = getParser(input);
        TurkishPseudoCodeParser.ForeachStatementContext ctx = parser.foreachStatement();
        ForeachStatement actual = visitor.visitForeachStatement(ctx);

        Assertions.assertEquals("item", actual.elementName, "Foreach element name mismatch.");
        Assertions.assertEquals("items", actual.collectionName, "Foreach collection name mismatch.");
    }

    @Test
    public void testVarDeclarationStatement() {
        // Test variable declaration statement: "değişken x = 42;"
        String input = "değişken x = 42;";
        TurkishPseudoCodeParser parser = getParser(input);
        TurkishPseudoCodeParser.VarDeclarationContext ctx = parser.varDeclaration();
        VarDeclarationStatement actual = visitor.visitVarDeclaration(ctx);

        Assertions.assertEquals("x", actual.expr.left, "VarDeclaration variable name mismatch.");
        Assertions.assertEquals(Primary.wrap(new Primary.Number(Integer.valueOf(42))), actual.expr.right, "VarDeclaration assignment mismatch.");
    }

    @Test
    public void testFunctionCallStatement() {
        // Test function call statement: "myFunction(1,2);"
        String input = "myFunction(1,2);";
        TurkishPseudoCodeParser parser = getParser(input);
        TurkishPseudoCodeParser.ExprStatementContext ctx = parser.exprStatement();
        Object result = visitor.visitExprStatement(ctx);
        Assertions.assertInstanceOf(FunctionCallExpr.class, result, "Result is not a FunctionCallExpr.");
        FunctionCallExpr actual = (FunctionCallExpr) result;

        Assertions.assertEquals("myFunction", actual.functionName, "Function call name mismatch.");
        Assertions.assertEquals(2, actual.args.length, "Function call argument count mismatch.");
        Assertions.assertEquals(Primary.wrap(new Primary.Number(Integer.valueOf(1))), actual.args[0], "First argument mismatch in function call.");
        Assertions.assertEquals(Primary.wrap(new Primary.Number(Integer.valueOf(2))), actual.args[1], "Second argument mismatch in function call.");
    }

    @Test
    public void testAssignmentStatement() {
        // Test assignment statement: "x = 42;"
        String input = "x = 42;";
        TurkishPseudoCodeParser parser = getParser(input);
        TurkishPseudoCodeParser.ExprStatementContext ctx = parser.exprStatement();
        Object result = visitor.visitExprStatement(ctx);
        Assertions.assertInstanceOf(AssignmentExpr.class, result, "Result is not an AssignmentExpr.");
        AssignmentExpr actual = (AssignmentExpr) result;

        Assertions.assertEquals("x", actual.left, "Assignment variable name mismatch.");
        Assertions.assertEquals(Primary.wrap(new Primary.Number(Integer.valueOf(42))), actual.right, "Assignment value mismatch.");
    }
}
