package me.yusuf.ecommerce_builder.shared.types;

import me.yusuf.ecommerce_builder.shared.types.Tuple7;

import java.util.Objects;

public class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> extends Tuple7<T1, T2, T3, T4, T5, T6, T7> {
    public final T8 _8;

    public Tuple8(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        super(_1, _2, _3, _4, _5, _6, _7);
        this._8 = _8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple8)) return false;
        if (!super.equals(o)) return false;
        Tuple8<?, ?, ?, ?, ?, ?, ?, ?> tuple8 = (Tuple8<?, ?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(_8, tuple8._8);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _8);
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ", " + _3 + ", " + _4 + ", " + _5 + ", " + _6 + ", " + _7 + ", " + _8 + ")";
    }
}
