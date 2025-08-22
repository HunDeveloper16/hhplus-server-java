package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.model.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCouponId(String couponId);

    default Coupon getValidateCouponByCouponId(String couponId) {
        return findByCouponId(couponId).orElseThrow(() -> new NotFoundException("쿠폰 정보를 찾을 수 없습니다."));
    }

    // FETCH JOIN. Coupon + IssuedCoupon
    @Query("SELECT c FROM Coupon c LEFT JOIN FETCH c.issuedCoupons ic WHERE c.id = :couponId")
    Optional<Coupon> findWithIssuedCouponsById(@Param("couponId") String couponId);

    default Coupon getValidateWithIssuedCouponByCouponId(String couponId) {
        return findWithIssuedCouponsById(couponId).orElseThrow(() -> new NotFoundException("쿠폰 정보를 찾을 수 없습니다."));
    }

}
