package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.model.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
