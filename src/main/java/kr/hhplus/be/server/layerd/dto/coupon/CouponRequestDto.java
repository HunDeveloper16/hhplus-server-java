package kr.hhplus.be.server.layerd.dto.coupon;

import kr.hhplus.be.server.common.exception.InvalidRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CouponRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Issue {
        private String couponId;
        private String userId;

        public void validateRequest() {
            if(userId==null) {
                throw new InvalidRequestException("사용자 아이디가 없습니다.");
            }

            if(couponId==null){
                throw new InvalidRequestException("쿠폰 정보가 없습니다.");
            }
        }
    }

}
