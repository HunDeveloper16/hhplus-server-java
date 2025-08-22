package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.model.coupon.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

    List<IssuedCoupon> findByCouponId(String couponId);

}
