package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.VarDeclarationStatement;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeLexer;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TranspilerTests {
    ASTBuilderVisitor astBuilder = new ASTBuilderVisitor();
    @Test
    public void testVarDecl(){
        var in = "değişken a = 4;";
        var lexer = new TurkishPseudoCodeLexer(CharStreams.fromString(in));
        var tokenStream =  new CommonTokenStream(lexer);
        var parser = new TurkishPseudoCodeParser(tokenStream);
        var varDecl = astBuilder.visitVarDeclaration(parser.varDeclaration());
        Assertions.assertEquals("a", varDecl.expr.left);
        Assertions.assertEquals("4", varDecl.expr.right.toString());
    }
}
