package kr.hhplus.be.server.layerd.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderResult {

    private List<ProductOrderItem> productOrderItems;
    private Long totalAmount;

    public static ProductOrderResult of(List<ProductOrderItem> productOrderItems, Long totalAmount) {
        return new ProductOrderResult(productOrderItems, totalAmount);
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductOrderItem{
        private Long productStockSeqNo; // 재고 일련 번호
        private String productName;
        private Long quantity;
        private BigDecimal totalAmount; // 각 상품에 대한 총 금액
    }


}
