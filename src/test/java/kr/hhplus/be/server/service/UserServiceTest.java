package kr.hhplus.be.server.service;

import kr.hhplus.be.server.common.exception.MinusPointException;
import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.model.user.User;
import kr.hhplus.be.server.model.user.UserBalance;
import kr.hhplus.be.server.repository.user.UserBalanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /**
     * 1. 실제 데이터베이스에 연결하지 않는 가짜 Repository
     * 2. 기본적으로 모든 메서드는 null이나 기본값을 반환
     * 3. when().thenReturn()으로 특정 상황에서 원하는 값을 반환하도록 설정 가능
     */
    @Mock
    private UserBalanceRepository userBalanceRepository;

    /**
     * 실제 UserService를 생성하면서 @Mock으로 만든 가짜 객체들을 자동으로 주입.
     * UserService userService = new UserService(userBalanceRepository); // 실제로 가짜 repository를 주입하여 작동
     */
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("존재하지 않는 회원의 잔액 조회시 에러가 발생한다.")
    public void test1() {
        //given
        String userId = "non_exist_user";

        when(userBalanceRepository.getValidateUserBalanceByUserId(userId)).thenThrow(new NotFoundException("회원 잔액 정보를 찾을 수 없습니다."));

        // when & then
        assertThrows(NotFoundException.class, () -> userService.getUserBalance(userId));
    }

    @Test
    @DisplayName("음수의 잔액 충전 시 충전이 실패한다.")
    public void test2() {
        // given
        long chargePoint = -1000;
        long currentPoint = 5000;
        UserBalance userBalance = UserBalance.builder()
                .user(new User(1L, "test_id"))
                .balance(currentPoint)
                .build();

        // when
        assertThrows(MinusPointException.class, () -> userBalance.addBalance(chargePoint));
        assertEquals(currentPoint, userBalance.getBalance()); // 잔액 변경 없음
    }
}
