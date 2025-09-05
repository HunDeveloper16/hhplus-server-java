package kr.hhplus.be.server.layerd.repository.order;

import kr.hhplus.be.server.layerd.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
