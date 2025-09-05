package kr.hhplus.be.server.clean.domain.repository;

import kr.hhplus.be.server.clean.domain.model.Order;

public interface OrderRepository {

    Order save(Order order);

}
