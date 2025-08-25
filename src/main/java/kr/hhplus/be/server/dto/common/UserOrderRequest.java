package kr.hhplus.be.server.dto.common;

import kr.hhplus.be.server.dto.order.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {

    private String orderNo;
    private String userId;
    private Long totalAmount;

    // 주문 요청 -> 회원 주문 요청
    public static UserOrderRequest of(String orderNo, OrderRequestDto.Order order,Long totalAmount){
        return UserOrderRequest.builder()
                .orderNo(orderNo)
                .userId(order.getUserId())
                .totalAmount(totalAmount)
                .build();
    }

}
