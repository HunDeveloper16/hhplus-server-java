package kr.hhplus.be.server.model.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.StockHistoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_stock_history")
public class ProductStockHistory {

    @Id
    @Column(name = "SEQ_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_SEQ_NO", nullable = false)
    private ProductStock productStock;

    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 10)
    private StockHistoryType type;

    @Column(name = "ORDER_NO", nullable = false)
    private String orderNo;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;


}
