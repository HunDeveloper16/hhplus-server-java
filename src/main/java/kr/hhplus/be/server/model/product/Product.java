package kr.hhplus.be.server.model.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.ProductStatus;
import kr.hhplus.be.server.common.enums.StockHistoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "SEQ_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @Column(name = "PRODUCT_ID", nullable = false, length = 50)
    private String productId;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 10)
    private ProductStatus status;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    public boolean isCloseStatus(){
        return this.status.equals(ProductStatus.CLOSE);
    }

    // 연관관계 매핑 (1:N)
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ProductStock> stocks;


}