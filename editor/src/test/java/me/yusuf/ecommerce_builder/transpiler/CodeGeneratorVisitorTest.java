package me.yusuf.ecommerce_builder.transpiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.lang.reflect.Type;

import org.junit.jupiter.api.Test;

import me.yusuf.ecommerce_builder.transpiler.ast.Block;
import me.yusuf.ecommerce_builder.transpiler.ast.VarDeclarationStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.AssignmentExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.FunctionCallExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.IfStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.LoopStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.ForeachStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.UnaryExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.PostfixExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;

public class CodeGeneratorVisitorTest {

    // Helper: create a simple FunctionCallExpr with no arguments.
    private FunctionCallExpr createSimpleFuncCall(String name) {
        FunctionCallExpr fce = new FunctionCallExpr();
        fce.functionName = name;
        fce.args = new Expression[0];
        return fce;
    }

    @Test
    public void testVisitPluginDef() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        PluginDef pd = new PluginDef();
        pd.name = "Test";
        pd.hookedMethod = "dummyMethod";
        // Create an empty block.
        pd.block = new Block();
        String expected = "public class TestPlugin implements Runnable {\n" +
                          "    @Override\n" +
                          "    public void run() {\n" +
                          "    }\n" +
                          "}\n";
        String result = visitor.visitPluginDef(pd);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitBlock() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        Block block = new Block();
        // Add a statement: an if statement.
        IfStatement ifStmt = new IfStatement();
        // For condition, use a function call "cond" with no args.
        ifStmt.condition = createSimpleFuncCall("cond");
        // Create an empty happyPath block.
        ifStmt.happyPath = new Block();
        block.statements.add(ifStmt);
        String expected = "if(cond()) {\n" +
                          "}\n";
        String result = visitor.visitBlock(block);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitVarDeclarationStatement() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        VarDeclarationStatement vds = new VarDeclarationStatement();
        AssignmentExpr assign = new AssignmentExpr();
        assign.left = "x";
        // Use a function call expression as the right-hand side.
        assign.right = createSimpleFuncCall("dummyFunc");
        vds.expr = assign;
        String expected = "var x = dummyFunc();";
        String result = visitor.visitVarDeclarationStatement(vds);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitAssignmentExpr() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        AssignmentExpr asn = new AssignmentExpr();
        asn.left = "y";
        // For right-hand side, use a FunctionCallExpr.
        asn.right = createSimpleFuncCall("func");
        String expected = "y = func()";
        String result = visitor.visitAssignmentExpr(asn);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitFunctionCallExpr() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        FunctionCallExpr fce = new FunctionCallExpr();
        fce.functionName = "g";
        fce.args = new Expression[0];
        String expected = "g()";
        String result = visitor.visitFunctionCallExpr(fce);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitIfStatement() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        IfStatement ifs = new IfStatement();
        // Condition: function call "cond"
        ifs.condition = createSimpleFuncCall("cond");
        // happyPath: empty block.
        Block happy = new Block();
        ifs.happyPath = happy;
        // No sadPath.
        String expected = "if(cond()) {\n" +
                          "}";
        String result = visitor.visitIfStatement(ifs);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitLoopStatement() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        LoopStatement ls = new LoopStatement();
        // Condition: function call "whileCond"
        ls.condition = createSimpleFuncCall("whileCond");
        // Block: empty
        ls.block = new Block();
        String expected = "while(whileCond()) {\n" +
                          "}";
        String result = visitor.visitLoopStatement(ls);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitForeachStatement() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        ForeachStatement fe = new ForeachStatement();
        fe.elementName = "e";
        fe.collectionName = "coll";
        fe.block = new Block();
        String expected = "for(var e : coll) {\n" +
                "}";
        String result = visitor.visitForeachStatement(fe);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitUnaryExpr() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        // Test with "değil" operator
        UnaryExpr ue = new UnaryExpr("değil", createSimpleFuncCall("dummy"));
        // visitUnaryExpr should convert "değil" to "!" and wrap the result of visit on operand.
        String expected = "(!dummy())";
        String result = visitor.visitUnaryExpr(ue);
        assertEquals(expected, result);

        // Test with "-" operator.
        UnaryExpr ue2 = new UnaryExpr("-", createSimpleFuncCall("dummy2"));
        expected = "(-dummy2())";
        result = visitor.visitUnaryExpr(ue2);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitPostfixExpr() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        // Create a PostfixExpr with primary as a function call "p" and hasNot = true.
        PostfixExpr pe = new PostfixExpr(createSimpleFuncCall("p"), true);
        // Expected: visitExpression(primary) returns "p();" and then adds "!".
        String expected = "p()!";
        String result = visitor.visitPostfixExpr(pe);
        assertEquals(expected, result);
    }

    @Test
    public void testGetMethodArguementTypes() {
        CodeGeneratorVisitor visitor = new CodeGeneratorVisitor();
        // Construct a dummy source string with a run method declaration.
        // For example: "public void run(java.lang.String arg, java.lang.Integer num) {"
        String source = "public void run(java.lang.String arg, java.lang.Integer num) {";
        Type[] expected;
        try {
            expected = new Type[] {
                Class.forName("java.lang.String"),
                Class.forName("java.lang.Integer")
            };
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Type[] result = visitor.getMethodArguementTypes(source);
        assertArrayEquals(expected, result);
    }
}
