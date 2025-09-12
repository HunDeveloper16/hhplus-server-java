package kr.hhplus.be.server.common.exception;

public class CouponQuantityExceededException extends RuntimeException {
    public CouponQuantityExceededException(String message) {
        super(message);
    }
}
