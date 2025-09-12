package kr.hhplus.be.server.clean.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItem {

    private Long seqNo;
    private Long productStockSeqNo;
    private String orderNo;
    private String productName;
    private Long quantity;
    private BigDecimal totalAmount;
    private LocalDateTime regDt;

    private OrderItem(Long productStockSeqNo, String orderNo, String productName, Long quantity, BigDecimal totalAmount, LocalDateTime regDt){
        this.productStockSeqNo = productStockSeqNo;
        this.orderNo = orderNo;
        this.productName = productName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.regDt = regDt;
    }

    public static OrderItem create(Long productStockSeqNo, String orderNo, String productName, Long quantity, BigDecimal totalAmount){
        return new OrderItem(productStockSeqNo,orderNo,productName,quantity,totalAmount,LocalDateTime.now());
    }

    public void assignId(Long id){
        this.seqNo = id;
    }

    // getter
    public Long getProductStockSeqNo(){
        return productStockSeqNo;
    }

    public String getOrderNo(){
        return orderNo;
    }
    public String getProductName(){
        return productName;
    }
    public Long getQuantity(){
        return quantity;
    }
    public BigDecimal getTotalAmount(){
        return totalAmount;
    }
    public LocalDateTime getRegDt(){
        return regDt;
    }

}
