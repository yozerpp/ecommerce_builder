package me.yusuf.ecommerce_builder.editor.types.completions;

public record EntityCompletion(String type, String description, boolean readOnly, FieldCompletion[] members) implements ISymbolValueCompletion {
    @Override
    public String name() {
        return type;
    }
}