package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.model.coupon.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {
}
