package me.yusuf.ecommerce_builder.editor.types.completions;

public record ParameterCompletion(String name, String description, IValueCompletion valueCompletion) implements ISymbolCompletion{
}
