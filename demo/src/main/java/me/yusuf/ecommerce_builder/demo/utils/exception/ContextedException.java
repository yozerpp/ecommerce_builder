package me.yusuf.ecommerce_builder.demo.utils.exception;


import me.yusuf.ecommerce_builder.shared.types.tuple.ITuple1;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple1;

public class ContextedException extends RuntimeException {
    public final ITuple1<?> context;
    public final Throwable exception;
    public ContextedException(ITuple1<?> context,Throwable exception) {
        this.exception = exception;
        this.context = context;
    }
}
