package kr.hhplus.be.server.repository.user;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.model.user.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    @Query("SELECT ub FROM UserBalance ub JOIN ub.user u WHERE u.userId = :userId")
    Optional<UserBalance> findByUserId(@Param("userId") String userId);

    default UserBalance getValidateUserBalanceByUserId(String userId){
        return findByUserId(userId).orElseThrow(() -> new NotFoundException("회원 잔액 정보를 찾을 수 없습니다."));
    }

    /**
     * 비관적 락(Pessimistic Lock) 적용
     * 조회 시 DB에서 해당 행 잠금. 타 트랜잭션이 해당 row를 수정하려 하면 커밋 전까지 대기
     *
     * @param userId 유저 아이디
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE) // 쓰기시에만 락 적용. 읽기시에 락 미적용
    @Query("SELECT ub FROM UserBalance ub JOIN ub.user u WHERE u.userId = :userId")
    Optional<UserBalance> findByUserIdForUpdate(@Param("userId") String userId);

    default UserBalance getValidateUserBalanceByUserIdForUpdate(String userId){
        return findByUserIdForUpdate(userId).orElseThrow(() -> new NotFoundException("회원 잔액 정보를 찾을 수 없습니다."));
    }

}
