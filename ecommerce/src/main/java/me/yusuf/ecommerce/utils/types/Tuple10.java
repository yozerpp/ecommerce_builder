package me.yusuf.ecommerce.utils.types;

import java.util.Objects;

public class Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    public final T10 _10;

    public Tuple10(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10) {
        super(_1, _2, _3, _4, _5, _6, _7, _8, _9);
        this._10 = _10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple10)) return false;
        if (!super.equals(o)) return false;
        Tuple10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> tuple10 = (Tuple10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(_10, tuple10._10);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _10);
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ", " + _3 + ", " + _4 + ", " + _5 + ", " + _6 + ", " + _7 + ", " + _8 + ", " + _9 + ", " + _10 + ")";
    }
}
