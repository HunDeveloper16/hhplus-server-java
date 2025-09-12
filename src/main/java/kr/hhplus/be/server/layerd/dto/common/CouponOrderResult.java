package kr.hhplus.be.server.layerd.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponOrderResult {

    private BigDecimal discountAmount; // 할인 금액
    private BigDecimal totalAmount; // 할인이 적용된 총 주문 금액

}
