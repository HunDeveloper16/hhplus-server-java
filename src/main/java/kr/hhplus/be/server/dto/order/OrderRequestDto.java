package kr.hhplus.be.server.dto.order;

import lombok.Getter;

import java.util.List;

public class OrderRequestDto {

    @Getter
    public static class Order{
        private String userId;
        private List<OrderProduct> orderProductList;

        public List<String> getProductIdList() {
            return orderProductList.stream().map(OrderProduct::getProductId).toList();
        }
    }

    @Getter
    public static class OrderProduct{
        private String productId;
        private Long quantity;
    }

}
