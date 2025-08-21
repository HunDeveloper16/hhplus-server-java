package kr.hhplus.be.server.service;

import jakarta.persistence.EntityManager;
import kr.hhplus.be.server.common.enums.BalanceType;
import kr.hhplus.be.server.dto.user.UserRequestDto;
import kr.hhplus.be.server.model.user.User;
import kr.hhplus.be.server.model.user.UserBalance;
import kr.hhplus.be.server.model.user.UserBalanceHistory;
import kr.hhplus.be.server.repository.user.UserBalanceHistoryRepository;
import kr.hhplus.be.server.repository.user.UserBalanceRepository;
import kr.hhplus.be.server.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test") // application-test.yml 적용
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserBalanceHistoryRepository userBalanceHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 잔액을 충전하면 내역이 쌓이고 조회시 충전된 잔액이 정상적으로 조회된다.")
    public void chargeAndGetBalance_Integration() {
        try {
            // given
            String testId = "test_id";
            long currentPoint = 3000;
            long chargePoint = 5000;

            User user = User.builder()
                    .userId(testId)
                    .build();
            userRepository.save(user);

            UserBalance userBalance = UserBalance.builder()
                    .user(user)
                    .balance(currentPoint)
                    .build();
            userBalanceRepository.save(userBalance);

            UserRequestDto.Charge chargeReqeust = new UserRequestDto.Charge(testId, chargePoint);

            // when - 실제 서비스 호출
            userService.chargeUserBalance(chargeReqeust);

            long resultBalance = userService.getUserBalance(testId);

            // then
            assertEquals(currentPoint + chargePoint, resultBalance);

            // 히스토리 확인
            List<UserBalanceHistory> histories = userBalanceHistoryRepository.findByUserId(testId);
            assertEquals(1, histories.size());
            assertEquals(testId, histories.get(0).getUserId());
            assertEquals(chargePoint, histories.get(0).getAmount());
            assertEquals(BalanceType.CHARGE, histories.get(0).getType());

        } finally {
            // 수동 정리
            userBalanceHistoryRepository.deleteAll();
            userBalanceRepository.deleteAll();
        }

    }
}
