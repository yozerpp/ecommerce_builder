package me.yusuf.ecommerce_builder.editor.types.completions;

public interface IValueCompletion extends ICompletion {
    String type();
    boolean readOnly();
}
