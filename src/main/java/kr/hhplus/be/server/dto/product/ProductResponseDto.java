package kr.hhplus.be.server.dto.product;

import kr.hhplus.be.server.model.product.Product;
import kr.hhplus.be.server.model.product.ProductStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class ProductResponseDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo{
        private String productId;
        private String name;
        private Long price;
        private Long stock;

        public static ProductInfo from(ProductStock stock){
            return ProductInfo.builder()
                    .productId(stock.getProduct().getProductId())
                    .name(stock.getProduct().getName())
                    .price(stock.getProduct().getPrice())
                    .stock(stock.getStock())
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentSalesProduct{
        private String productId;
        private String productName;
        private Long totalSales;

        public static RecentSalesProduct from(Product product){
            return RecentSalesProduct.builder()
                    .productId(product.getProductId())
                    .productName(product.getName())
                    .totalSales(product.getTotalSales())
                    .build();
        }

    }

}
