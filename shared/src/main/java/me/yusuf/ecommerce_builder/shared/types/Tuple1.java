package me.yusuf.ecommerce_builder.shared.types;

import java.util.Objects;

public class Tuple1<T1> {
    public final T1 _1;

    public Tuple1(T1 _1) {
        this._1 = _1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple1)) return false;
        Tuple1<?> tuple1 = (Tuple1<?>) o;
        return Objects.equals(_1, tuple1._1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1);
    }

    @Override
    public String toString() {
        return "(" + _1 + ")";
    }
}
