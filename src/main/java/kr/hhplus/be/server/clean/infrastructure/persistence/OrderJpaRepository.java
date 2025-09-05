package kr.hhplus.be.server.clean.infrastructure.persistence;

import kr.hhplus.be.server.clean.domain.model.Order;
import kr.hhplus.be.server.clean.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderJpaRepository implements OrderRepository {

    private final SpringOrderJpa jpa; // 내부 JPA Repo ??

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = toEntity(order);
        OrderEntity saved = jpa.save(orderEntity);
        order.assignId(saved.seqNo);

        return order;
    }


    /* Domain -> JPA DTO 매핑 (Infra 전용) */
    private OrderEntity toEntity(Order order){

        return OrderEntity.from(order);
    }
}
