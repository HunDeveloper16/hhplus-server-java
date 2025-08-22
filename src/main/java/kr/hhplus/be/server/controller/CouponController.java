package kr.hhplus.be.server.controller;

import kr.hhplus.be.server.dto.coupon.CouponRequestDto;
import kr.hhplus.be.server.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/issue")
    public ResponseEntity<?> orderPayment(@PathVariable CouponRequestDto.Issue issue) {

        couponService.issueCouponsByArrivalOrder(issue);

        return ResponseEntity.ok().build();
    }

}
