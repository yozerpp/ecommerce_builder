package me.yusuf.ecommerce.utils.types;

import java.util.Objects;

public class Tuple2<T1, T2> extends Tuple1<T1> {
    public final T2 _2;

    public Tuple2(T1 _1, T2 _2) {
        super(_1);
        this._2 = _2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple2)) return false;
        if (!super.equals(o)) return false;
        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;
        return Objects.equals(_2, tuple2._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _2);
    }

    @Override
    public String toString() {
        return "(" + _1 + ", " + _2 + ")";
    }
}
