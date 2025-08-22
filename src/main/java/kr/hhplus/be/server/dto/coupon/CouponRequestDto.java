package kr.hhplus.be.server.dto.coupon;

import kr.hhplus.be.server.common.exception.InvalidRequestException;
import lombok.Getter;

public class CouponRequestDto {

    @Getter
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
