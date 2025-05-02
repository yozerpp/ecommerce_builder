package me.yusuf.ecommerce_builder.editor.transpiler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.lang.reflect.Type;

import me.yusuf.ecommerce_builder.editor.tool.transpiler.CodeGeneratorVisitor;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Primary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.Block;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.VarDeclarationStatement;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.AssignmentExpr;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.FunctionCallExpr;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.IfStatement;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.LoopStatement;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.ForeachStatement;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.UnaryExpr;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.PostfixExpr;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Expression;

public class CodeGeneratorVisitorTest {

    private static CodeGeneratorVisitor visitor;
    @BeforeAll
    static void init(){
//        visitor = new CodeGeneratorVisitor();
    }
    // Helper: create a simple FunctionCallExpr with no arguments.
    private FunctionCallExpr createSimpleFuncCall(String name) {
        FunctionCallExpr fce = new FunctionCallExpr();
        fce.setFunctionName( name);
        fce.setArgs( new Expression[0]);
        return fce;
    }
    private PostfixExpr createPostfixExpr(boolean hasNot){
        return new PostfixExpr(new Primary.Identifier("dummy",null),hasNot);
    }
    @Test
    public void testVisitPluginDef() {
        PluginDef pd = new PluginDef();
        pd.setName ("Test");
        pd.setHookedMethod("dummyMethod");
        // Create an empty block.
        pd.setBlock(new Block());
        String expected = """
                public class TestPlugin_0_v1 {
                    public static void run() {
                    }
                }
                """;
        String result = visitor.visitPluginDef(pd,new Type[0],0, 1);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitBlock() {
        Block block = new Block();
        // Add a statement: an if statement.
        IfStatement ifStmt = new IfStatement();
        // For condition, use a function call "cond" with no args.
        ifStmt.setCondition( createSimpleFuncCall("cond"));
        // Create an empty happyPath block.
        ifStmt.setBlock(new Block());
        block.getStatements().add(ifStmt);
        String expected = """
                if(cond()) {
                }
                """;
        String result = visitor.visitBlock(block);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitVarDeclarationStatement() {
        VarDeclarationStatement vds = new VarDeclarationStatement();
        vds.setVarName("x");
        // Use a function call expression as the right-hand side.
        vds.setValue(createSimpleFuncCall("dummyFunc"));
        String expected = "var x = dummyFunc();";
        String result = visitor.visitVarDeclarationStatement(vds);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitAssignmentExpr() {
        AssignmentExpr asn = new AssignmentExpr();
        asn.setLeft( "y");
        // For right-hand side, use a FunctionCallExpr.
        asn.setRight( createSimpleFuncCall("func"));
        String expected = "y = func()";
        String result = visitor.visitAssignmentExpr(asn);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitFunctionCallExpr() {
        FunctionCallExpr fce = new FunctionCallExpr();
        fce.setFunctionName("g");
        fce.setArgs(new Expression[0]);
        String expected = "g()";
        String result = visitor.visitFunctionCallExpr(fce);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitIfStatement() {
        IfStatement ifs = new IfStatement();
        // Condition: function call "cond"
        ifs.setCondition(createSimpleFuncCall("cond"));
        // happyPath: empty block.
        Block happy = new Block();
        ifs.setBlock(happy);
        // No sadPath.
        String expected = "if(cond()) {\n" +
                          "}";
        String result = visitor.visitIfStatement(ifs);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitLoopStatement() {
        LoopStatement ls = new LoopStatement();
        // Condition: function call "whileCond"
        ls.setCondition( createSimpleFuncCall("whileCond"));
        // Block: empty
        ls.setBlock( new Block());
        String expected = "while(whileCond()) {\n" +
                          "}";
        String result = visitor.visitLoopStatement(ls);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitForeachStatement() {
        ForeachStatement fe = new ForeachStatement();
        fe.setElementName( "e");
        fe.setCollection(new Primary.Identifier("coll"));
        fe.setBlock(new Block());
        String expected = "for(var e : coll) {\n" +
                "}";
        String result = visitor.visitForeachStatement(fe);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitUnaryExpr() {
        // Test with "değil" operator
        UnaryExpr ue = new UnaryExpr("+", createPostfixExpr(true));
        // visitUnaryExpr should convert "değil" to "!" and wrap the result of visit on operand.
        String expected = "+!dummy";
        String result = visitor.visitUnaryExpr(ue);
        assertEquals(expected, result);

        // Test with "-" operator.
        UnaryExpr ue2 = new UnaryExpr("-", createPostfixExpr(true));
        expected = "-!dummy";
        result = visitor.visitUnaryExpr(ue2);
        assertEquals(expected, result);
    }

    @Test
    public void testVisitPostfixExpr() {
        // Create a PostfixExpr with primary as a function call "p" and hasNot = true.
        PostfixExpr pe = new PostfixExpr(createSimpleFuncCall("p"), true);
        // Expected: visitExpression(primary) returns "p();" and then adds "!".
        String expected = "!p()";
        String result = visitor.visitPostfixExpr(pe);
        assertEquals(expected, result);
    }

    @Test
    public void testGetMethodArguementTypes() {
        // Construct a dummy source string with a run handle declaration.
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
