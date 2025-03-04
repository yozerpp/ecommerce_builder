package me.yusuf.ecommerce_builder.shared.types;

import me.yusuf.ecommerce_builder.shared.types.Tuple6;

import java.util.Objects;

public class Tuple7<T1, T2, T3, T4, T5, T6, T7> extends Tuple6<T1, T2, T3, T4, T5, T6> {
    public final T7 _7;

    public Tuple7(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        super(_1, _2, _3, _4, _5, _6);
        this._7 = _7;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple7)) return false;
        if (!super.equals(o)) return false;
        Tuple7<?, ?, ?, ?, ?, ?, ?> tuple7 = (Tuple7<?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(_7, tuple7._7);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _7);
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ", " + _3 + ", " + _4 + ", " + _5 + ", " + _6 + ", " + _7 + ")";
    }
}
