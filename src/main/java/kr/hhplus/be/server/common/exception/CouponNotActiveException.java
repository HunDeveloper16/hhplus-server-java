package kr.hhplus.be.server.common.exception;

public class CouponNotActiveException extends RuntimeException {
    public CouponNotActiveException(String message) {
        super(message);
    }
}
