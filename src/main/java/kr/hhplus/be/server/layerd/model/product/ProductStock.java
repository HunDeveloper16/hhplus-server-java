package kr.hhplus.be.server.layerd.model.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.ProductCloseException;
import kr.hhplus.be.server.common.exception.ProductStockOverException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product_stock")
public class ProductStock {

    @Id
    @Column(name = "SEQ_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_SEQ_NO", nullable = false)
    private Product product;

    @Column(name = "STOCK", nullable = false)
    private Long stock = 0L;

    @Column(name = "MOD_DT")
    private LocalDateTime modDt;

    public void validateProductStatus() {
        if(product.isCloseStatus()){
            log.info("판매 종료된 상품 주문 시도. 상품 아이디: {}", product.getProductId());
            throw new ProductCloseException("상품이 판매 종료 상태입니다.");
        }
    }

    public void validateProductStock(Long orderQuantity){
        if(stock - orderQuantity < 0) {
            log.info("상품 재고 오류 발생. 상품 아이디. {}, 요청 수량: {}", product.getProductId(), orderQuantity);
            throw new ProductStockOverException("주문 수량이 상품 재고를 초과합니다.");
        }
    }

    public void reduceStock(long quantity){
        validateProductStock(quantity);

        this.stock = stock - quantity;
    }

    public void reduceStockByOrder(long quantity){
        validateProductStatus();

        reduceStock(quantity);
    }

}

