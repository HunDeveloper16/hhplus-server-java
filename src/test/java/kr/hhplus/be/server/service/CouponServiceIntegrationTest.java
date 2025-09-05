package kr.hhplus.be.server.service;

import kr.hhplus.be.server.common.enums.CouponStatus;
import kr.hhplus.be.server.common.enums.DiscountType;
import kr.hhplus.be.server.layerd.dto.coupon.CouponRequestDto;
import kr.hhplus.be.server.layerd.model.coupon.Coupon;
import kr.hhplus.be.server.layerd.model.coupon.IssuedCoupon;
import kr.hhplus.be.server.layerd.repository.coupon.CouponRepository;
import kr.hhplus.be.server.layerd.repository.coupon.IssuedCouponRepository;
import kr.hhplus.be.server.layerd.service.CouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
public class CouponServiceIntegrationTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private IssuedCouponRepository issuedCouponRepository;
    @Test
    @Transactional
    @DisplayName("쿠폰이 정상적으로 발급되어 발급 쿠폰이 저장된다.")
    public void test1() {
        String couponId = "test_coupon";
        String userId = "test_user";
        // given
        CouponRequestDto.Issue couponRequestDto = CouponRequestDto.Issue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();

        // id 설정 금지 -> 테스트 실패
        Coupon coupon = Coupon.builder()
                .name("test_coupon_name")
                .couponId(couponId) // request의 couponId와 일치
                .discountType(DiscountType.FIXED)
                .discountValue(BigDecimal.valueOf(10))
                .maxDiscountPercent(BigDecimal.valueOf(10))
                .totalQuantity(100)
                .status(CouponStatus.ACTIVE)
                .regDt(LocalDateTime.now())
                .build();

        couponRepository.save(coupon);

        // when
        couponService.issueCouponsByArrivalOrder(couponRequestDto);

        // then
        Optional<IssuedCoupon> issuedCoupon = issuedCouponRepository.findByCouponIdAndUserId(couponId, userId);

        // 발급 쿠폰이 존재하는지 검증
        assertTrue(issuedCoupon.isPresent(), "발급 쿠폰이 저장되어야 한다.");
        assertEquals(couponId, issuedCoupon.get().getCouponId(), "발급 쿠폰의 아이디와 실제 쿠폰의 아이디가 일치한다.");
    }

}
