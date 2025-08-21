package kr.hhplus.be.server.repository.product;

import kr.hhplus.be.server.model.product.ProductStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockHistoryRepository extends JpaRepository<ProductStockHistory, Long> {
}
