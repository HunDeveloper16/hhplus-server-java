package kr.hhplus.be.server.model.order;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

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
    private Integer quantity;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Long totalAmount;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

}