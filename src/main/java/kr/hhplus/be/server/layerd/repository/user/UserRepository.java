package kr.hhplus.be.server.layerd.repository.user;

import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.layerd.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    default User getValidateUserByUserId(String userId){
        return findByUserId(userId).orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
    }

}
