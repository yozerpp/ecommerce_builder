package me.yusuf.ecommerce_builder.shared.types.exception;

public class UnauthorizedException extends ExceptionBase{
    public UnauthorizedException(ExceptionCause cause) {
        super(cause);
    }
    public UnauthorizedException(String message, ExceptionCause cause) {
        super(message, cause);
    }
    public UnauthorizedException(String message) {
        super(message,null);
    }

}
