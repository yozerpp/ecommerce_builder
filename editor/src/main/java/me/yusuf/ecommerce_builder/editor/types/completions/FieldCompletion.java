package me.yusuf.ecommerce_builder.editor.types.completions;

public record FieldCompletion(String name, String type ,String description, boolean readOnly) implements ISymbolValueCompletion {

}
