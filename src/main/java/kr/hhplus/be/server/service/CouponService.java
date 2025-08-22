package kr.hhplus.be.server.service;


import kr.hhplus.be.server.dto.coupon.CouponRequestDto;
import kr.hhplus.be.server.dto.coupon.CouponResponseDto;
import kr.hhplus.be.server.model.coupon.Coupon;
import kr.hhplus.be.server.model.coupon.IssuedCoupon;
import kr.hhplus.be.server.repository.coupon.CouponRepository;
import kr.hhplus.be.server.repository.coupon.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;

    /**
     * 선착순으로 쿠폰을 발급합니다.
     *
     * @param request 발급 요청 정보
     */
    @Transactional
    public void issueCouponsByArrivalOrder(CouponRequestDto.Issue request) {
        // 요청 값 검증
        request.validateRequest();

        // 쿠폰 정보 조회. FETCH JOIN
        Coupon coupon = couponRepository.getValidateWithIssuedCouponByCouponId(request.getCouponId());

        // 쿠폰 발급 검증 ( 상태, 수량, 중복 발급 )
        coupon.validateCouponForNewIssue(request.getUserId());

        // 쿠폰 발급 처리
        IssuedCoupon issuedCoupon = coupon.createIssuedCoupon(request.getUserId());
        issuedCouponRepository.save(issuedCoupon);
    }

    /**
     * 쿠폰 목록을 조회합니다.
     *
     * @param userId 유저 아이디
     */
    public List<CouponResponseDto.UserCoupon> getUserCouponList(String userId){
        return issuedCouponRepository.findByCouponId(userId).stream().map(CouponResponseDto.UserCoupon::from).toList();
    }

}
