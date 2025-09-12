package kr.hhplus.be.server.service;

import kr.hhplus.be.server.layerd.repository.coupon.CouponRepository;
import kr.hhplus.be.server.layerd.repository.coupon.CouponUsageHistoryRepository;
import kr.hhplus.be.server.layerd.repository.coupon.IssuedCouponRepository;
import kr.hhplus.be.server.layerd.service.CouponService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private IssuedCouponRepository issuedCouponRepository;
    @Mock
    private CouponUsageHistoryRepository couponUsageHistoryRepository;

    @InjectMocks
    private CouponService couponService;

//    @Test
//    @DisplayName("쿠폰 발급 시 존재하지 않는 쿠폰아이디 요청시 에러가 발생한다.")
//    public void issueCoupon_shouldThrowNotFoundException_ifCouponIdDoesNotExist(){
//        // given
//        String couponId = "not_exist_id";
//        String userId = "test_user";
//
//        CouponRequestDto.Issue couponRequestDto = CouponRequestDto.Issue.builder()
//                .couponId(couponId)
//                .userId(userId)
//                .build();
//        when(couponRepository.findByCouponIdWithPessimisticLock(couponId)).thenReturn(Optional.empty());
//
//        // when & then
//        assertThrows(NotFoundException.class, () -> couponService.issueCouponsByArrivalOrder(couponRequestDto));
//    }

//    @Test
//    @DisplayName("쿠폰 발급 수량이 최대 수치를 초과할때 에러가 발생한다.")
//    public void issueCoupon_shouldThrowCouponQuantityExceededException_ifMaxQuantityExceeded(){
//        // given
//        String couponId = "not_exist_id";
//        String userId = "test_user";
//        Integer maxTotalQuantity = 100;
//        Integer currentIssuedCount = 100;
//
//        CouponRequestDto.Issue couponRequestDto = CouponRequestDto.Issue.builder()
//                .couponId(couponId)
//                .userId(userId)
//                .build();
//
//        Coupon mockCoupon = Coupon.builder()
//                .name("test_coupon")
//                .couponId(couponId) // request의 couponId와 일치
//                .discountType(DiscountType.FIXED)
//                .maxDiscountPercent(BigDecimal.valueOf(10))
//                .totalQuantity(maxTotalQuantity)
//                .status(CouponStatus.ACTIVE)
//                .regDt(LocalDateTime.now())
//                .build();
//
//        when(couponRepository.findByCouponIdWithPessimisticLock(couponId))
//                .thenReturn(Optional.of(mockCoupon));
//        when(issuedCouponRepository.countIssuedCoupons(couponId))
//                .thenReturn(currentIssuedCount);
//
//        // when & then
//        assertThrows(CouponQuantityExceededException.class, () -> couponService.issueCouponsByArrivalOrder(couponRequestDto));
//    }

}
