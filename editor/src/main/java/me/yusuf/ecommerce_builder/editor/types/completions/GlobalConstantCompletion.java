package me.yusuf.ecommerce_builder.editor.types.completions;

public record GlobalConstantCompletion(String name, String type, String description, Object value, boolean readOnly) implements ISymbolValueCompletion {
}
