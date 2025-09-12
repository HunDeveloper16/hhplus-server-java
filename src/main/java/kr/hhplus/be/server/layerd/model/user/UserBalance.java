package kr.hhplus.be.server.layerd.model.user;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.InsufficientBalanceException;
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

    public void addBalance(long balance){
        if(balance < 0L){
            throw new MinusPointException("음수의 포인트는 충전될 수 없습니다.");
        }

        this.balance += balance;
        this.modDt = LocalDateTime.now();
    }

    public void deductBalance(Long balance){
        if(this.balance < balance) {
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }

        this.balance -= balance;
        this.modDt = LocalDateTime.now();
    }

}