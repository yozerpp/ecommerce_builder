package me.yusuf.ecommerce_builder.editor.transpiler;

import me.yusuf.ecommerce_builder.editor.transpiler.ASTBuilderVisitor;
import me.yusuf.ecommerce_builder.editor.transpiler.CodeGeneratorVisitor;
import me.yusuf.ecommerce_builder.shared.components.EditorContextHolder;
import me.yusuf.ecommerce_builder.shared.types.PluginSourceAndMetadata;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.editor.transpiler.generated.TurkishPseudoCodeLexer;
import me.yusuf.ecommerce_builder.editor.transpiler.generated.TurkishPseudoCodeParser;

public class IntegrationTest {

    // Helper method to perform the integration:
    // Pseudo-code --> AST --> Generated Code.
    private PluginSourceAndMetadata generatePluginFromSource(String source) {
        CharStream charStream = CharStreams.fromString(source);
        TurkishPseudoCodeLexer lexer = new TurkishPseudoCodeLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TurkishPseudoCodeParser parser = new TurkishPseudoCodeParser(tokens);
        PluginDef pluginDef = new ASTBuilderVisitor().visitIşlevTanımı(parser.işlevTanımı());
        return new CodeGeneratorVisitor().generate(pluginDef, EditorContextHolder.getEditorId());
    }

    @Test
    public void testSimplePlugin() {
        String pseudoCode =
            "SEPET me.yusuf.ecommerce_builder.transpiler.IntegrationTest.SAMPLE.something'da hataEx hatasında yap Simple {" +
            "    değ x = 5;" +
            "    yazdır(x);" +
            "}";
        PluginSourceAndMetadata pluginSourceAndMetadata = generatePluginFromSource(pseudoCode);

        String expected =
            "public class " + EditorContextHolder.getEditorId() + "_SimplePlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        var x = 5;\n" +
            "        yazdır(x);\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, pluginSourceAndMetadata.source());
    }

    @Test
    public void testComplexPlugin() throws Exception {
        String pseudoCode =
            "SEPET me.yusuf.ecommerce_builder.transpiler.IntegrationTest.SAMPLE.something'da hataEx hatasında yap Complex {" +
            "    değ a = 10;" +
            "    değ b = 20;" +
            "    eğer a < b ise {" +
            "         yazdır(\"a is less\");" +
            "    } değilse {" +
            "         yazdır(\"a is not less\");" +
            "    }" +
            "    a != b iken {" +
            "         a = a + 1;" +
            "    }" +
            "    items içindeki her item için {" +
            "         yazdır(item);" +
            "    }" +
            "}";
        PluginSourceAndMetadata pluginSourceAndMetadata = generatePluginFromSource(pseudoCode);

        String expected =
            "public class " + EditorContextHolder.getEditorId() + "_ComplexPlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        var a = 10;\n" +
            "        var b = 20;\n" +
            "        if(a < b) {\n" +
            "            yazdır(\"a is less\");\n" +
            "        } else {\n" +
            "            yazdır(\"a is not less\");\n" +
            "        }\n" +
            "        while(a != b) {\n" +
            "            a = a + 1;\n" +
            "        }\n" +
            "        for(var item : items) {\n" +
            "            yazdır(item);\n" +
            "        }\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, pluginSourceAndMetadata.source());
    }
    
    @Test
    public void testNestedIfs() throws Exception {
        String pseudoCode =
            "SEPET me.yusuf.ecommerce_builder.transpiler.IntegrationTest.SAMPLE.something'da hataEx hatasında yap Nested {" +
            "    değ x = 7;" +
            "    eğer x < 10 ise {" +
            "         yazdır(\"less\");" +
            "         eğer x == 7 ise {" +
            "              yazdır(\"equal to seven\");" +
            "         }" +
            "    } değilse {" +
            "         yazdır(\"not less\");" +
            "    }" +
            "}";
        PluginSourceAndMetadata pluginSourceAndMetadata = generatePluginFromSource(pseudoCode);
        String expected =
            "public class " + EditorContextHolder.getEditorId() + "_NestedPlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        var x = 7;\n" +
            "        if(x < 10) {\n" +
            "            yazdır(\"less\");\n" +
            "            if(x == 7) {\n" +
            "                yazdır(\"equal to seven\");\n" +
            "            }\n" +
            "        } else {\n" +
            "            yazdır(\"not less\");\n" +
            "        }\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, pluginSourceAndMetadata.source());
    }
    
    @Test
    public void testArithmeticExpressions() {
        String pseudoCode =
            "SEPET me.yusuf.ecommerce_builder.transpiler.IntegrationTest.SAMPLE.something'da sonrasında yap Arithmetic {" +
            "    değ sum = 1 + 2 - 3 * 4 / 2;" +
            "    yazdır(sum);" +
            "}";
        PluginSourceAndMetadata pluginSourceAndMetadata = generatePluginFromSource(pseudoCode);
        String expected =
            "public class " + EditorContextHolder.getEditorId() + "_ArithmeticPlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        var sum = 1 + 2 - 3 * 4 / 2;\n" +
            "        yazdır(sum);\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, pluginSourceAndMetadata.source());
    }
    
    @Test
    public void testFunctionCallNoArgs() {
        String pseudoCode =
            "SEPET me.yusuf.ecommerce_builder.transpiler.IntegrationTest.SAMPLE.something'den sonrasında yap NoArgs {" +
            "    testFunction();" +
            "}";
                PluginSourceAndMetadata pluginSourceAndMetadata = generatePluginFromSource(pseudoCode);
        String expected =
            "public class " + EditorContextHolder.getEditorId() + "_NoArgsPlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        testFunction();\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, pluginSourceAndMetadata.source());
    }
    
    @Test
    public void testFunctionCallMultipleArgs() throws Exception {
        String pseudoCode =
            "SEPET me.yusuf.ecommerce_builder.transpiler.IntegrationTest.SAMPLE.something'den sonrasında yap MultiArgs {" +
            "    testFunction(10, 20 + 5, \"hello\");" +
            "}";
        PluginSourceAndMetadata pluginSourceAndMetadata = generatePluginFromSource(pseudoCode);
        String expected =
            "public class " + EditorContextHolder.getEditorId() + "_MultiArgsPlugin implements Runnable {\n" +
            "    @Override\n" +
            "    public void run() {\n" +
            "        testFunction(10, 20 + 5, \"hello\");\n" +
            "    }\n" +
            "}\n";
        assertEquals(expected, pluginSourceAndMetadata.source());
    }
    public static class SAMPLE{
        void something(){

        }
    }
}
