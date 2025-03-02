package me.yusuf.ecommerce.utils.exception;


import me.yusuf.ecommerce.utils.types.Tuple1;

public class ContextedException extends RuntimeException {
    public final Tuple1<?> context;
    public final Throwable exception;
    public ContextedException(Tuple1<?> context,Throwable exception) {
        this.exception = exception;
        this.context = context;
    }
}
