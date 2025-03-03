package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.expression.PostfixExpr;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeLexer;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTests extends TestBase {
    ASTBuilderVisitor visitor = new ASTBuilderVisitor();
    @Test
    public void testPostfixExpr(){
        var in = "5 değil";
        var parser = getParser(in);
        var postfix = parser.postfixExpr();
        Assertions.assertNotNull(postfix.DEĞİL());
        Assertions.assertEquals("5", visitor.visitPrimary(postfix.primary()).toString());
        //more complex
    }

}
