package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.ASTNode;
import me.yusuf.ecommerce_builder.transpiler.ast.Block;
import me.yusuf.ecommerce_builder.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Primary;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.UnaryExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.FunctionCallExpr;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PluginDefTests extends TestBase {

    ASTBuilderVisitor visitor = new ASTBuilderVisitor();

    @Test
    public void testComplexPluginDef() {
        // Create a complex pluginDef instance from the grammar.
        // Grammar for pluginDef: IDENTIFIER (hataExpr | SONRA) block;
        // hataExpr: IDENTIFIER HATA;
        // We will use the hataExpr variant.
        // Our sample input will include a function call inside the block.
        // Input:
        //   MyPlugin MyException hatasında { myFunction(42); }
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
        Assertions.assertEquals("myFunction(42)", block.statements.get(0).toString());

        // The single statement should be a function call expression.
        // Our visitor constructs ExpressionStatement from function calls.
        // We downcast it to FunctionCallExpr.
        Object stmt = block.statements.getFirst();
        Assertions.assertInstanceOf(FunctionCallExpr.class, stmt, "The statement is not an instance of FunctionCallExpr");
        FunctionCallExpr funcCall = (FunctionCallExpr) stmt;
        // Assert that functionName is "myFunction"
        Assertions.assertEquals("myFunction", funcCall.functionName);
        // Assert that the function call has exactly one argument.
        Assertions.assertNotNull(funcCall.args, "Function arguments are null");
        Assertions.assertEquals(1, funcCall.args.length);

        // The argument should be an expression representing the number 42.
        Expression argExpr = funcCall.args[0];
        var p  =new Primary.Number();
        p.number = 42;
        Assertions.assertEquals(Primary.wrap(p),argExpr);
        // Typically, the visitor constructs a UnaryExpr wrapping a PostfixExpr wrapping a Primary.Number.
        // We can get its string form or, ideally, directly access the fields.
        // In this case, we check that converting the expression to string gives "42".
        // Alternatively, we can downcast if we know the structure.
        // We'll downcast if possible.
    }
}
