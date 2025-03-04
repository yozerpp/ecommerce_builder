package me.yusuf.ecommerce_builder.shared.types.exception;

public class NotFoundException extends ExceptionBase {
    public NotFoundException(ExceptionCause cause) {
        super(cause);
    }
    public NotFoundException(ExceptionCause cause, String message) {
        super(message, cause);
    }
    public NotFoundException(String message) {
        super(message,null);
    }
}
