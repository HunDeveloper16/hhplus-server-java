package kr.hhplus.be.server.layerd.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@Table(name = "`user`") // 예약어 처리
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    // User → UserBalance 1:N
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserBalance> balances = new ArrayList<>();

}
