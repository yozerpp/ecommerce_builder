package me.yusuf.ecommerce.utils.types;

import java.util.Objects;

public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {
    public final T3 _3;

    public Tuple3(T1 _1, T2 _2, T3 _3) {
        super(_1, _2);
        this._3 = _3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple3)) return false;
        if (!super.equals(o)) return false;
        Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
        return Objects.equals(_3, tuple3._3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _3);
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ", " + _3 + ")";
    }
}
