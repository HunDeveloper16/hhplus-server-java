package kr.hhplus.be.server.clean.infrastructure.persistence;

import jakarta.persistence.*;
import kr.hhplus.be.server.clean.domain.model.OrderItem;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seqNo;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_SEQ_NO", nullable = false)
    OrderEntity order;

    @Column(name = "PRODUCT_STOCK_SEQ_NO", nullable = false)
    Long productStockSeqNo;

    @Column(name = "ORDER_NO", nullable = false, length = 50)
    String orderNo;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 50)
    String productName;

    @Column(name = "QUANTITY", nullable = false)
    Long quantity;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    BigDecimal totalAmount;

    @Column(name = "REG_DT", nullable = false)
    LocalDateTime regDt;


    public static OrderItemEntity from(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .productStockSeqNo(orderItem.getProductStockSeqNo())
                .orderNo(orderItem.getOrderNo())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .totalAmount(orderItem.getTotalAmount())
                .regDt(orderItem.getRegDt())
                .build();
    }

}