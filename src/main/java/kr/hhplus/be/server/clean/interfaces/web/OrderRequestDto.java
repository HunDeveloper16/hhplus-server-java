package kr.hhplus.be.server.clean.interfaces.web;

import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.common.exception.OrderItemQuantityZeroException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderRequestDto {

    @Getter
    public static class Order{
        private String userId;
        private String couponId;
        private List<OrderProduct> orderProductList;

        public void validateOrderItem(){
            validateEmptyOrderItem();

            validateOrderItemQuantity();
        }

        public void validateEmptyOrderItem(){
            if(orderProductList.isEmpty()){
                throw new NotFoundException("요청 주문 상품이 없습니다.");
            }
        }

        public void validateOrderItemQuantity(){
            orderProductList.forEach(p -> {
                if(p.getQuantity().equals(0L)){
                    log.info("요청 주문 상품 중 수량이 0개인 상품이 있습니다. 주문 상품: {}, 주문 수량: {}", p.getProductId(), p.getQuantity());
                    throw new OrderItemQuantityZeroException("요청 주문 상품 중 수량이 0개인 상품이 있습니다.");
                }
            });
        }

    }

    @Getter
    public static class OrderProduct{
        private String productId;
        private Long quantity;
    }

}
