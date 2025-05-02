package me.yusuf.ecommerce_builder.editor.tool.compiler;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {
    private final String sourceCode;

    // For source code
    public InMemoryJavaFileObject(String className, String sourceCode) {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}

