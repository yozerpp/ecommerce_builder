package me.yusuf.ecommerce_builder.shared.types.tuple;

public record Tuple1<T1>(T1 _1) implements ITuple1<T1> {
    @Override
    public String toString() {
        return "(" + _1 + ")";
    }
}
