package kr.hhplus.be.server.clean.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/* 순수 POJO 도메인 */
public class Order {

    private Long seqNo;
    private final String orderNo;
    private final String userId;
    private final BigDecimal totalAmount;
    private final BigDecimal discountAmount;
    private final LocalDateTime regDt;
    private final List<OrderItem> orderItems;

    private Order(String orderNo, String userId, BigDecimal totalAmount, BigDecimal discountAmount, LocalDateTime regDt,List<OrderItem> orderItems) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.regDt = regDt;
        this.orderItems = orderItems;
    }

    public static Order create(String orderNo, String userId, BigDecimal totalAmount, BigDecimal discountAmount,List<OrderItem> orderItems){
        return new Order(orderNo,userId,totalAmount,discountAmount,LocalDateTime.now(),orderItems);
    }

    public void assignId(Long id){
        this.seqNo = id;
    }


    // getter
    public String getOrderNo(){return this.orderNo;}
    public String getUserId(){return this.userId;}
    public BigDecimal getTotalAmount(){return this.totalAmount;}
    public BigDecimal getDiscountAmount(){return this.discountAmount;}
    public LocalDateTime getRegDt(){return this.regDt;}
    public List<OrderItem> getOrderItems(){return this.orderItems;}
}
