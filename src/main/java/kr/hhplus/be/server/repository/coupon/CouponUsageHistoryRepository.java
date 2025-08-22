package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.model.coupon.CouponUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUsageHistoryRepository extends JpaRepository<CouponUsageHistory, Long> {
}
