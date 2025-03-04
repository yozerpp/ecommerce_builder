package me.yusuf.ecommerce_builder.transpiler;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import me.yusuf.ecommerce_builder.transpiler.ASTBuilderVisitor;
import me.yusuf.ecommerce_builder.transpiler.CodeGeneratorVisitor;
import me.yusuf.ecommerce_builder.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeLexer;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;
import me.yusuf.ecommerce_builder.transpiler.Plugin;

public class IntegrationTest {

    // Helper method to perform the integration:
    // Pseudo-code --> AST --> Generated Code.
    private Plugin generatePluginFromSource(String source) throws Exception {
        CharStream charStream = CharStreams.fromString(source);
        TurkishPseudoCodeLexer lexer = new TurkishPseudoCodeLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TurkishPseudoCodeParser parser = new TurkishPseudoCodeParser(tokens);
        PluginDef pluginDef = new ASTBuilderVisitor().visitPluginDef(parser.pluginDef());
        return new CodeGeneratorVisitor().generate(pluginDef);
    }

    @Test
    public void testSimplePlugin() throws Exception {
        String pseudoCode =
            "run hataEx yap Simple {" +
            "    değişken x = 5;" +
            "    yazdır x;" +
            "}";
        Plugin plugin = generatePluginFromSource(pseudoCode);

        String expected =
            "public class SimplePlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        var x = 5;\n" +
            "        yazdır(x);\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, plugin.source());
    }

    @Test
    public void testComplexPlugin() throws Exception {
        String pseudoCode =
            "run hataEx yap Complex {" +
            "    değişken a = 10;" +
            "    değişken b = 20;" +
            "    eğer a < b ise {" +
            "         yazdır \"a is less\";" +
            "    } değilse {" +
            "         yazdır \"a is not less\";" +
            "    }" +
            "    olduğu sürece a != b {" +
            "         a = a + 1;" +
            "    }" +
            "    her item içindeki items için {" +
            "         yazdır item;" +
            "    }" +
            "}";
        Plugin plugin = generatePluginFromSource(pseudoCode);

        String expected =
            "public class ComplexPlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        var a = 10;\n" +
            "        var b = 20;\n" +
            "        if(a<b) {\n" +
            "            yazdır(\"a is less\");\n" +
            "        } else {\n" +
            "            yazdır(\"a is not less\");\n" +
            "        }\n" +
            "        while(a!=b) {\n" +
            "            a = (a + 1);\n" +
            "        }\n" +
            "        for(var item : items) {\n" +
            "            // body\n" +
            "        }\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, plugin.source());
    }
}
