package kr.hhplus.be.server.clean.infrastructure.persistence;

import jakarta.persistence.*;
import kr.hhplus.be.server.clean.domain.model.Order;
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
public class OrderEntity {

    @Id
    @Column(name = "SEQ_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seqNo;

    @Column(name = "ORDER_NO", nullable = false, length = 50)
    String orderNo;

    @Column(name = "USER_ID", nullable = false, length = 50)
    String userId;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    BigDecimal totalAmount;

    @Column(name = "DISCOUNT_AMOUNT", nullable = false)
    BigDecimal discountAmount;

    @Column(name = "REG_DT", nullable = false)
    LocalDateTime regDt;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addOrderItem(OrderItemEntity orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void addOrderItems(List<OrderItemEntity> orderItems) {
        orderItems.forEach(this::addOrderItem);
    }

    public static OrderEntity from(Order order) {
        List<OrderItemEntity> itemEntities = order.getOrderItems().stream()
                .map(OrderItemEntity::from)
                .toList();

        OrderEntity entity = OrderEntity.builder()
                .orderNo(order.getOrderNo())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .regDt(order.getRegDt())
                .build();

        // 양방향 연관관계 설정
        entity.addOrderItems(itemEntities);

        return entity;
    }

}