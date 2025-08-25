package kr.hhplus.be.server.repository.coupon;

import kr.hhplus.be.server.model.coupon.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

    List<IssuedCoupon> findByCouponId(String couponId);

    @Query("SELECT COUNT(ic) FROM IssuedCoupon ic WHERE ic.couponId = :couponId")
    int countIssuedCoupons(@Param("couponId") String couponId);

    Optional<IssuedCoupon> findByUserId(String userId);

    @Query("SELECT EXISTS(SELECT 1 FROM IssuedCoupon ic WHERE ic.couponId = :couponId AND ic.userId = :userId)")
    boolean existsByCouponIdAndUserId(@Param("couponId") String couponId, @Param("userId") String userId);

    Optional<IssuedCoupon> findByCouponIdAndUserId(String couponId, String userId);

}
