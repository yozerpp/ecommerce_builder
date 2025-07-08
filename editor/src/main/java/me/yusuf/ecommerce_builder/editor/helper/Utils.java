package me.yusuf.ecommerce_builder.editor.helper;

import me.yusuf.ecommerce_builder.editor.tool.transpiler.generated.TurkishPseudoCodeLexer;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.generated.TurkishPseudoCodeParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;


public interface Utils {
    static TurkishPseudoCodeParser getParser(String in){
        return new TurkishPseudoCodeParser(new CommonTokenStream(new TurkishPseudoCodeLexer(CharStreams.fromString(in))));
    }
    static String[] getStaticClassSource(Class<?>[] classes){
        String[] sources = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            sources[i] = getStaticClassSource(classes[i]);
        }
        return sources;
    }
    static String getStaticClassSource(Class<?> cls) {
        try(var classIs = Thread.currentThread().getContextClassLoader().getResourceAsStream("source/" +cls.getName().replace('.','/')+ ".java")) {
            return Charset.defaultCharset().decode(ByteBuffer.wrap( classIs.readAllBytes())).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
