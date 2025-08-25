package kr.hhplus.be.server.repository.coupon;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.model.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
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

    /**
     * 쓰기 비관적 락 적용
     * lock 타임아웃 3초로 설정.
     *
     * @param couponId 쿠폰 아이디
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId")
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "3000"),  // 3초
    })
    Optional<Coupon> findByCouponIdWithPessimisticLock(@Param("couponId") String couponId);

}
