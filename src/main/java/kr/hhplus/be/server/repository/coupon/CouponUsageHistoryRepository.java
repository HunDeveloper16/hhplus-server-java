package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.model.coupon.CouponUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponUsageHistoryRepository extends JpaRepository<CouponUsageHistory, Long> {
}
