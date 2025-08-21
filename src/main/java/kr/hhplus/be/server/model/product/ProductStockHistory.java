package kr.hhplus.be.server.model.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.StockHistoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
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
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 10)
    private StockHistoryType type;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

}
