package me.yusuf.ecommerce_builder.shared.types;

public record Tuple2<T1, T2>(T1 _1, T2 _2) {
    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ")";
    }
}
