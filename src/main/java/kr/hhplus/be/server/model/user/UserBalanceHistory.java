package kr.hhplus.be.server.model.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.BalanceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_balance_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceHistory {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BALANCE_SEQ_NO", nullable = false)
    private UserBalance userBalance;

    @Column(name = "AMOUNT", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 10)
    private BalanceType type;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    // 정적 팩토리 메서드
    public static UserBalanceHistory ofCharge(UserBalance userBalance, Long amount) {
        UserBalanceHistory history = new UserBalanceHistory();
        history.userBalance = userBalance;
        history.amount = amount;
        history.type = BalanceType.CHARGE;
        history.regDt = LocalDateTime.now();
        return history;
    }

    public static UserBalanceHistory ofUse(UserBalance userBalance, Long amount) {
        UserBalanceHistory history = new UserBalanceHistory();
        history.userBalance = userBalance;
        history.amount = amount;
        history.type = BalanceType.USE;
        history.regDt = LocalDateTime.now();
        return history;
    }

}