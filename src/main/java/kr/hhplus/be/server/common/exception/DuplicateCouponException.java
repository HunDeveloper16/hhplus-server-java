package kr.hhplus.be.server.common.exception;

public class DuplicateCouponException extends RuntimeException {
    public DuplicateCouponException(String message) {
        super(message);
    }
}
