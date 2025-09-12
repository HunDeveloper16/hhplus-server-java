package kr.hhplus.be.server.clean.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringOrderJpa extends JpaRepository<OrderEntity, Long> {
}
