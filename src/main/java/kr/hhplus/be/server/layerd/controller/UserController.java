package kr.hhplus.be.server.layerd.controller;

import kr.hhplus.be.server.layerd.dto.user.UserRequestDto;
import kr.hhplus.be.server.layerd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원 잔액 조회
     *
     * @param userId 유저 아이디
     * @return 회원 잔액
     */
    @GetMapping("/{userId}/balance")
    public ResponseEntity<Long> getBalance(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserBalance(userId));
    }

    /**
     * 회원 잔액 충전
     *
     * @param request 충전 요청 정보
     */
    @PostMapping("/balance/charge")
    public ResponseEntity<?> chargeBalance(@RequestBody UserRequestDto.Charge request) {
        userService.chargeUserBalance(request);

        return ResponseEntity.ok().build();
    }

}
