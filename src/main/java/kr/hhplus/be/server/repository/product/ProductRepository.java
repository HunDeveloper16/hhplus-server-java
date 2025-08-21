package kr.hhplus.be.server.repository.product;

import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(String productId);

}
