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

    @Query("SELECT ub FROM UserBalance ub JOIN ub.user u WHERE u.userId = :userId")
    Optional<UserBalance> findByUserIdForUpdate(@Param("userId") String userId);

    default UserBalance getValidateUserBalanceByUserIdForUpdate(String userId){
        return findByUserIdForUpdate(userId).orElseThrow(() -> new NotFoundException("회원 잔액 정보를 찾을 수 없습니다."));
    }

}
