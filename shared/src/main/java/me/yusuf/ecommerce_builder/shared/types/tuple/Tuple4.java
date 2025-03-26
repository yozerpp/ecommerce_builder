package me.yusuf.ecommerce_builder.shared.types.tuple;

public record Tuple4<T1, T2, T3, T4>(T1 _1, T2 _2, T3 _3, T4 _4) {
    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ", " + _3 + ", " + _4 + ")";
    }
}
