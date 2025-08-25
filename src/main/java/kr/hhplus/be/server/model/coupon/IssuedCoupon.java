package kr.hhplus.be.server.model.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.IssuedCouponStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "issued_coupon")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_SEQ_NO", nullable = false)
    private Coupon coupon;

    @Column(name = "COUPON_ID", nullable = false, length = 50)
    private String couponId;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "COUPON_CODE", nullable = false, unique = true, length = 50)
    private String couponCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 20)
    private IssuedCouponStatus status = IssuedCouponStatus.ISSUED;

    @Column(name = "REG_DT", nullable = false, updatable = false)
    private LocalDateTime regDt;

    // 쿠폰 사용 내역 (1:N)
    @OneToMany(mappedBy = "issuedCoupon", fetch = FetchType.LAZY)
    private List<CouponUsageHistory> usageHistories;

    public static IssuedCoupon of(Coupon coupon, String userId) {
        return IssuedCoupon.builder()
                .coupon(coupon)
                .couponId(coupon.getCouponId())
                .userId(userId)
                .couponCode(generateCouponCode())
                .status(IssuedCouponStatus.ISSUED)
                .build();
    }

    private static String generateCouponCode() {
        return String.format("COUPON_%d", System.currentTimeMillis());
    }

    public boolean isValid() {
        return status == IssuedCouponStatus.ISSUED || status == IssuedCouponStatus.USED;
    }

}
