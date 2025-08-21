package kr.hhplus.be.server.model.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.MinusPointException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "user_balance")
@NoArgsConstructor
@AllArgsConstructor
public class UserBalance {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ_NO", nullable = false)
    private User user;

    @Column(name = "BALANCE", nullable = false)
    private Long balance;

    @Column(name = "MOD_DT")
    private LocalDateTime modDt;

    // 잔액을 충전하고 히스토리를 생성합니다.
    public UserBalanceHistory addBalance(long balance){
        if(balance < 0L){
            throw new MinusPointException("음수의 포인트는 충전될 수 없습니다.");
        }

        this.balance += balance;
        this.modDt = LocalDateTime.now();

        return UserBalanceHistory.ofCharge(this, balance);
    }

    // 잔액을 차감하고 히스토리를 생성합니다.
    public UserBalanceHistory minusBalance(Long balance){
        this.balance -= balance;
        this.modDt = LocalDateTime.now();

        return UserBalanceHistory.ofUse(this, balance);
    }

}