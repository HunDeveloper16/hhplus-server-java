package kr.hhplus.be.server.layerd.model.coupon;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_usage_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponUsageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ_NO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUED_COUPON_SEQ_NO", nullable = false)
    private IssuedCoupon issuedCoupon;

    @Column(name = "COUPON_ID", nullable = false, length = 50)
    private String couponId;

    @Column(name = "ORDER_NO", length = 50)
    private String orderNo;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "ORIGINAL_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal originalAmount;

    @Column(name = "DISCOUNT_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "FINAL_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount;

    @Column(name = "REG_DT", nullable = false, updatable = false)
    private LocalDateTime regDt;

}