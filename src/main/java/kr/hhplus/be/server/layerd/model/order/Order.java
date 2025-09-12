package kr.hhplus.be.server.layerd.model.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`order`") // order는 예약어이므로 백틱 처리
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @Column(name = "ORDER_NO", nullable = false, length = 50)
    private String orderNo;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "DISCOUNT_AMOUNT", nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "REG_DT", nullable = false)
    private LocalDateTime regDt;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void addOrderItems(List<OrderItem> orderItems) {
        orderItems.forEach(this::addOrderItem);
    }

}