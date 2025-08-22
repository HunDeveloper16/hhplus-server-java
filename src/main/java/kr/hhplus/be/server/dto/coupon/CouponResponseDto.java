package kr.hhplus.be.server.dto.coupon;

import kr.hhplus.be.server.model.coupon.IssuedCoupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CouponResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCoupon {
        private String name;
        private String couponId;
        private String couponCode;

        public static UserCoupon from(IssuedCoupon issuedCoupon){
            return UserCoupon.builder()
                    .name(issuedCoupon.getCoupon().getName())
                    .couponId(issuedCoupon.getCouponId())
                    .couponCode(issuedCoupon.getCouponCode())
                    .build();
        }
    }

}
