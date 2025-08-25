package kr.hhplus.be.server.dto.common;

import kr.hhplus.be.server.dto.order.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderRequest {

    private String orderNo;
    private List<OrderProduct> orderProductList;

    // 주문 요청 -> 상품 주문 요청
    public static ProductOrderRequest of(String orderNo,OrderRequestDto.Order order){
        return ProductOrderRequest.builder()
                .orderNo(orderNo)
                .orderProductList(order.getOrderProductList().stream().map(OrderProduct::from).toList())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderProduct{
        private String productId;
        private Long quantity;

        public static OrderProduct from(OrderRequestDto.OrderProduct product){
            return OrderProduct.builder()
                    .productId(product.getProductId())
                    .quantity(product.getQuantity())
                    .build();
        }
    }

}
