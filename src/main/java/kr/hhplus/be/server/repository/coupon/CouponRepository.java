package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.model.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCouponId(String couponId);

    default Coupon getValidateCouponByCouponId(String couponId) {
        return findByCouponId(couponId).orElseThrow(() -> new NotFoundException("쿠폰 정보를 찾을 수 없습니다."));
    }

}
