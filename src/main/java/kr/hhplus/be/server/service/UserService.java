package kr.hhplus.be.server.service;

import kr.hhplus.be.server.dto.user.UserRequestDto;
import kr.hhplus.be.server.model.user.UserBalance;
import kr.hhplus.be.server.model.user.UserBalanceHistory;
import kr.hhplus.be.server.repository.user.UserBalanceHistoryRepository;
import kr.hhplus.be.server.repository.user.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceHistoryRepository userBalanceHistoryRepository;

    /**
     * 회원 잔액을 조회합니다.
     *
     * @param userId 유저 아이디
     */
    @Transactional(readOnly = true)
    public Long getUserBalance(String userId) {
        log.info(" ======= 1. 회원 잔액을 조회합니다. ======= ");
        UserBalance userBalance = userBalanceRepository.getValidateUserBalanceByUserId(userId);

        return userBalance.getBalance();
    }

    /**
     * 회원 잔액을 충전합니다.
     *
     * @param request 충전 요청 정보
     */
    @Transactional
    public void chargeUserBalance(UserRequestDto.Charge request){
        log.info(" ======= 1. 회원 잔액을 조회합니다. ======= ");
        UserBalance userBalance = userBalanceRepository.getValidateUserBalanceByUserIdForUpdate(request.getUserId());

        UserBalanceHistory userBalanceHistory = userBalance.addBalance(request.getAmount()); // dirtyChecking

        log.info(" ======= 2. 충전 내역을 저장합니다. ======= ");
        userBalanceHistoryRepository.save(userBalanceHistory);
    }

}
