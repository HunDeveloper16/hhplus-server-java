package kr.hhplus.be.server.layerd.controller;

import kr.hhplus.be.server.layerd.dto.coupon.CouponRequestDto;
import kr.hhplus.be.server.layerd.dto.coupon.CouponResponseDto;
import kr.hhplus.be.server.layerd.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    /**
     * 쿠폰을 발급합니다.
     *
     * 요구사항: 사용자는 선착순으로 할인 쿠폰을 발급받을 수 있습니다.
     *
     * @param issue 발급 요청 정보
     */
    @PostMapping("/issue")
    public ResponseEntity<?> couponIssue(@RequestBody CouponRequestDto.Issue issue) {

        couponService.issueCouponsByArrivalOrder(issue);

        return ResponseEntity.ok().build();
    }

    /**
     * 보유 쿠폰 목록을 조회합니다.
     *
     * @param userId 발급 요청 정보
     */
    @PostMapping("/me/{userId}")
    public ResponseEntity<List<CouponResponseDto.UserCoupon>> getUserCouponList(@PathVariable String userId) {

        return ResponseEntity.ok(couponService.getUserCouponList(userId));

    }

}
