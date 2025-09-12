package kr.hhplus.be.server.layerd.repository.user;

import kr.hhplus.be.server.layerd.model.user.UserBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBalanceHistoryRepository extends JpaRepository<UserBalanceHistory, Long> {

    List<UserBalanceHistory> findByUserId(String userId);

}
