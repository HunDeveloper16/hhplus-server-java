package kr.hhplus.be.server.common.exception;

public class OrderItemQuantityZeroException extends RuntimeException {
    public OrderItemQuantityZeroException(String message) {
        super(message);
    }
}
