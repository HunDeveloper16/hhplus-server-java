package kr.hhplus.be.server.dto.coupon;

import lombok.Getter;

public class CouponRequestDto {

    @Getter
    public static class Issue {
        private String couponId;
        private String userId;
    }
}
