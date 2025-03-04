package me.yusuf.ecommerce_builder.shared.types;

import java.util.Objects;

public class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> {
    public final T9 _9;

    public Tuple9(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        super(_1, _2, _3, _4, _5, _6, _7, _8);
        this._9 = _9;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple9)) return false;
        if (!super.equals(o)) return false;
        Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ?> tuple9 = (Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(_9, tuple9._9);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _9);
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ", " + _3 + ", " + _4 + ", " + _5 + ", " + _6 + ", " + _7 + ", " + _8 + ", " + _9 + ")";
    }
}
