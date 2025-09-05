package kr.hhplus.be.server.layerd.model.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.CouponStatus;
import kr.hhplus.be.server.common.enums.DiscountType;
import kr.hhplus.be.server.common.exception.CouponNotActiveException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "COUPON_ID", nullable = false, length = 50)
    private String couponId;

    @Enumerated(EnumType.STRING)
    @Column(name = "DISCOUNT_TYPE", nullable = false, length = 20)
    private DiscountType discountType;

    @Column(name = "DISCOUNT_VALUE", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "MAX_DISCOUNT_PERCENT", precision = 10, scale = 2)
    private BigDecimal maxDiscountPercent;

    @Column(name = "TOTAL_QUANTITY", nullable = false)
    private Integer totalQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20)
    @Builder.Default
    private CouponStatus status = CouponStatus.ACTIVE;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    // 발급 쿠폰 연관관계 (1:N)
//    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
//    private List<IssuedCoupon> issuedCoupons;


    // 쿠폰 발급시 유효성 검증
    public void validateCouponForNewIssue(String userId) {
        // 상태 검증
        validateCouponStatus();
        // 수량 검증
//        validateQuantityForNewIssue();
        // 중복 발급 검증
//        validateDuplicateIssue(userId);
    }

    // 쿠폰 발급 및 객체 생성
    public IssuedCoupon createIssuedCoupon(String userId) {
        validateCouponForNewIssue(userId);
        return IssuedCoupon.of(this, userId);
    }

    // 쿠폰 상태 검증
    public void validateCouponStatus() {
        if (status != CouponStatus.ACTIVE) {
            throw new CouponNotActiveException("비활성 상태의 쿠폰입니다.");
        }
    }

    // 발급 수량 검증 (한 번 더 발급 시 초과 여부)
//    private void validateQuantityForNewIssue() {
//        long currentValidCount = getValidIssuedCount();
//
//        if (currentValidCount + 1 > totalQuantity) {
//            throw new CouponQuantityExceededException("쿠폰 발급 최대 수량을 초과하였습니다.");
//        }
//    }

    // 중복 발급 검증
//    private void validateDuplicateIssue(String userId) {
//        boolean hasDuplicate = issuedCoupons.stream().anyMatch(issued -> issued.getUserId().equals(userId) && issued.isValid());
//
//        if (hasDuplicate) {
//            throw new DuplicateCouponException("이미 발급받은 쿠폰입니다.");
//        }
//    }

    // 유효한 발급 쿠폰 수량 계산
//    private long getValidIssuedCount() {
//        return issuedCoupons.stream()
//                .filter(IssuedCoupon::isValid) // ISSUED, USED 상태만
//                .count();
//    }


}
