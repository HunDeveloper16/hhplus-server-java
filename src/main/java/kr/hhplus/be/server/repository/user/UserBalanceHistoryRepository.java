package kr.hhplus.be.server.repository.user;

import kr.hhplus.be.server.model.user.UserBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceHistoryRepository extends JpaRepository<UserBalanceHistory, Long> {
}
