package kr.hhplus.be.server.common.exception;

public class ProductStockOverException extends RuntimeException{
    public ProductStockOverException(String message) {
        super(message);
    }
}
