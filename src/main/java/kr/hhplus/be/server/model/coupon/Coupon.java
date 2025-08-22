package kr.hhplus.be.server.model.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.CouponStatus;
import kr.hhplus.be.server.common.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private CouponStatus status = CouponStatus.ACTIVE;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    // 발급 쿠폰 연관관계 (1:N)
    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<IssuedCoupon> issuedCoupons;

}
