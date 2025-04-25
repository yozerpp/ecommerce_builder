package me.yusuf.ecommerce_builder.editor.helper;

import me.yusuf.ecommerce_builder.editor.transpiler.generated.TurkishPseudoCodeLexer;
import me.yusuf.ecommerce_builder.editor.transpiler.generated.TurkishPseudoCodeParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Utils {
    public static TurkishPseudoCodeParser getParser(String in){
        return new TurkishPseudoCodeParser(new CommonTokenStream(new TurkishPseudoCodeLexer(CharStreams.fromString(in))));
    }
}
