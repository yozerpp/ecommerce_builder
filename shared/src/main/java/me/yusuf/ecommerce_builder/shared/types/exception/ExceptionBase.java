package me.yusuf.ecommerce_builder.shared.types.exception;

public abstract class ExceptionBase extends Exception {
    public ExceptionCause cause_e;
    protected ExceptionBase(ExceptionCause cause_e) {
        this(null,cause_e);
    }
    protected ExceptionBase(String message, ExceptionCause cause_e) {
        super(message);
        this.cause_e = cause_e;;
    }
}
