package kr.hhplus.be.server.service;


import kr.hhplus.be.server.common.enums.DiscountType;
import kr.hhplus.be.server.common.exception.CouponQuantityExceededException;
import kr.hhplus.be.server.common.exception.DuplicateCouponException;
import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.dto.common.CouponOrderRequest;
import kr.hhplus.be.server.dto.common.CouponOrderResult;
import kr.hhplus.be.server.dto.coupon.CouponRequestDto;
import kr.hhplus.be.server.dto.coupon.CouponResponseDto;
import kr.hhplus.be.server.model.coupon.Coupon;
import kr.hhplus.be.server.model.coupon.CouponUsageHistory;
import kr.hhplus.be.server.model.coupon.IssuedCoupon;
import kr.hhplus.be.server.repository.coupon.CouponRepository;
import kr.hhplus.be.server.repository.coupon.CouponUsageHistoryRepository;
import kr.hhplus.be.server.repository.coupon.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponUsageHistoryRepository couponUsageHistoryRepository;

    /**
     * 선착순으로 쿠폰을 발급합니다.
     *
     * 쿠폰과 쿠폰 발급 정보를 FETCH JOIN하면 대용량 데이터일때, 문제가 될 수 있음.
     * 따라서 현재 size()만 필요한 상황이므로, 모두 FETCH JOIN하지않고 count()쿼리를 직접 실행.
     *
     * 1.락 타임아웃은 3초.
     * 2.전체 트랜잭션 처리시간 10초로 설정.
     *
     *
     * @param request 발급 요청 정보
     */
    @Transactional(timeout = 10)
    public void issueCouponsByArrivalOrder(CouponRequestDto.Issue request) {
        // 요청 값 검증
        request.validateRequest();

        try{
            // 쿠폰 정보 조회. lock 획득.
            Coupon coupon = couponRepository.findByCouponIdWithPessimisticLock(request.getCouponId())
                    .orElseThrow(() -> new NotFoundException("쿠폰 정보를 찾을 수 없습니다."));

            // 발급된 쿠폰 갯수 조회
            int issuedCouponCount = issuedCouponRepository.countIssuedCoupons(request.getCouponId());

            // 쿠폰 수량 검증
            if(issuedCouponCount + 1 > coupon.getTotalQuantity()) {
                throw new CouponQuantityExceededException("쿠폰 최대 발급 횟수를 초과하였습니다.");
            }

            // 중복 발급 검증
            if(issuedCouponCount > 0) {
                boolean isDuplicate = issuedCouponRepository.existsByCouponIdAndUserId(request.getCouponId(), request.getUserId());
                if (isDuplicate) {
                    throw new DuplicateCouponException("이미 발급 받은 쿠폰입니다.");
                }
            }
            // 쿠폰 발급 처리
            IssuedCoupon issuedCoupon = coupon.createIssuedCoupon(request.getUserId());
            issuedCouponRepository.save(issuedCoupon);
            // lock 반납.
        }catch (Exception e){
            log.error("쿠폰 발급 처리 실패. 쿠폰 아이디 : {} , 사용자 정보 : {}", request.getCouponId(), request.getUserId());
            throw e;
        }
    }

    /**
     * 쿠폰 목록을 조회합니다.
     *
     * @param userId 유저 아이디
     */
    public List<CouponResponseDto.UserCoupon> getUserCouponList(String userId){
        return issuedCouponRepository.findByCouponId(userId).stream().map(CouponResponseDto.UserCoupon::from).toList();
    }

    /**
     * 주문에 대해 할인을 적용합니다.
     *
     * 요구사항 : 주문 시에 유효한 할인 쿠폰을 함께 제출하면, 전체 주문금액에 대해 할인 혜택을 부여
     *
     * @param orderRequest 주문 요청 객체
     *
     * @return 쿠폰 적용 결과
     */
    @Transactional
    public CouponOrderResult applyCouponDiscount(CouponOrderRequest orderRequest) {
        IssuedCoupon issuedCoupon =  issuedCouponRepository.findByCouponIdAndUserId(orderRequest.getCouponId(), orderRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("쿠폰 정보를 찾을 수 없습니다."));

        // 상태 검증
        issuedCoupon.validateCouponStatus();

        BigDecimal finalAmount = BigDecimal.ZERO, discountAmount = BigDecimal.ZERO;

       // 정액
       if(issuedCoupon.getCoupon().getDiscountType().equals(DiscountType.FIXED)){
           finalAmount = BigDecimal.valueOf(orderRequest.getTotalAmount()).subtract(issuedCoupon.getCoupon().getDiscountValue());

           // 할인액 = 고정값
           discountAmount = issuedCoupon.getCoupon().getDiscountValue();
        }

       // 정률
        if(issuedCoupon.getCoupon().getDiscountType().equals(DiscountType.PERCENTAGE)){
            BigDecimal discountRate = issuedCoupon.getCoupon().getDiscountValue().divide(new BigDecimal("100"));
            finalAmount = BigDecimal.valueOf(orderRequest.getTotalAmount()).multiply(discountRate);

            // 할인액 = 기존 값 - 정률 적용 값
            discountAmount = BigDecimal.valueOf(orderRequest.getTotalAmount()).subtract(finalAmount);
        }

        // 쿠폰 사용 처리
        issuedCoupon.useCoupon();

        // 쿠폰 정보 저장
        issuedCouponRepository.save(issuedCoupon);

        // 쿠폰 사용 내역 저장
        couponUsageHistoryRepository.save(CouponUsageHistory.builder()
                .issuedCoupon(issuedCoupon)
                .couponId(orderRequest.getCouponId())
                .orderNo(orderRequest.getOrderNo())
                .userId(orderRequest.getUserId())
                .originalAmount(BigDecimal.valueOf(orderRequest.getTotalAmount()))
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .regDt(LocalDateTime.now())
                .build());

        return new CouponOrderResult(discountAmount, finalAmount);
    }

}
