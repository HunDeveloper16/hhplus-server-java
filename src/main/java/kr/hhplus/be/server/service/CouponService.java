package kr.hhplus.be.server.service;


import kr.hhplus.be.server.dto.coupon.CouponRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    /**
     * 선착순으로 쿠폰을 발급합니다.
     *
     * @param request 발급 요청 정보
     */
    public void issueCouponsByArrivalOrder(CouponRequestDto.Issue request) {

    }

}
