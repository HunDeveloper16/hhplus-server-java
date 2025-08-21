package kr.hhplus.be.server.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_balance")
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

    // UserBalance → UserBalanceHistory 1:N
//    @OneToMany(mappedBy = "userBalance", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserBalanceHistory> histories = new ArrayList<>();


    // 잔액을 충전하고 히스토리를 생성합니다.
    public UserBalanceHistory addBalance(Long balance){
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