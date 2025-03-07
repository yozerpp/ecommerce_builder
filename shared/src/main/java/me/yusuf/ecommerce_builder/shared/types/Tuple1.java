package me.yusuf.ecommerce_builder.shared.types;

public record Tuple1<T1>(T1 _1) {
    @Override
    public String toString() {
        return "(" + _1 + ")";
    }
}
