package kr.hhplus.be.server.layerd.dto.common;

import kr.hhplus.be.server.layerd.dto.order.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponOrderRequest {

    private String orderNo;
    private String couponId;
    private String userId;
    private Long totalAmount;

    // 주문 요청 -> 쿠폰 주문 요청
    public static CouponOrderRequest of(String orderNo,OrderRequestDto.Order order,Long totalAmount){
        return CouponOrderRequest.builder()
                .orderNo(orderNo)
                .couponId(order.getCouponId())
                .userId(order.getUserId())
                .totalAmount(totalAmount)
                .build();
    }


}
