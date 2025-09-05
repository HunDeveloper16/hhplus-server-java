package kr.hhplus.be.server.layerd.model.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.layerd.dto.common.ProductOrderResult;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_SEQ_NO", nullable = false)
    private Order order;

    @Column(name = "PRODUCT_STOCK_SEQ_NO", nullable = false)
    private Long productStockSeqNo;

    @Column(name = "ORDER_NO", nullable = false, length = 50)
    private String orderNo;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 50)
    private String productName;

    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    public static OrderItem from(ProductOrderResult.ProductOrderItem productOrderItem) {
        return OrderItem.builder()
                .productStockSeqNo(productOrderItem.getProductStockSeqNo())
                .productName(productOrderItem.getProductName())
                .quantity(productOrderItem.getQuantity())
                .totalAmount(productOrderItem.getTotalAmount())
                .regDt(LocalDateTime.now())
                .build();
    }

}