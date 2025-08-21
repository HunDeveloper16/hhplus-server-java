package kr.hhplus.be.server.model.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.enums.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "`order`") // order는 예약어이므로 백틱 처리
@Getter
public class Order {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @Column(name = "ORDER_NO", nullable = false, length = 50)
    private String orderNo;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Long totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    // 주문 -> 주문상품 1:N
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItem> items = new ArrayList<>();

}