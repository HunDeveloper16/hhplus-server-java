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
     * [동시성 문제]
     * 동시에 각각 1000원 500원을 충전하는 경우, 동시 요청으로 인해 아래 1.회원 잔액 조회에서 서로 동일한 잔액을 조회하게 됩니다.
     * 결과적으로 1000원이 먼저 반영되고, 500원이 반영될 것이기 때문에 결과는 1500원이 아닌 500원이 됩니다.
     *
     * [해결]
     * 따라서 일반적으로 잔액을 충전하는 행위는 데이터의 일관성, 정합성이 중요하다고 생각하여 "비관적 락"을 적용하였습니다.
     *
     * @param request 충전 요청 정보
     */
    @Transactional // 트랜잭션 보장 및 비관적 락 적용시 필요
    public void chargeUserBalance(UserRequestDto.Charge request){
        log.info(" ======= 1. 회원 잔액을 조회합니다. ======= ");
        UserBalance userBalance = userBalanceRepository.getValidateUserBalanceByUserIdForUpdate(request.getUserId()); // 비관적 락 적용

        UserBalanceHistory userBalanceHistory = userBalance.addBalance(request.getAmount()); // dirtyChecking

        log.info(" ======= 2. 충전 내역을 저장합니다. ======= ");
        userBalanceHistoryRepository.save(userBalanceHistory);
    }

}
