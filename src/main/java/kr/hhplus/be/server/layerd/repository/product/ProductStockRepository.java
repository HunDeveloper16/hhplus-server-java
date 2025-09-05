package kr.hhplus.be.server.layerd.repository.product;

import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.layerd.model.product.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    // FETCH JOIN. 두번 조회 방지
    @Query("SELECT ps FROM ProductStock ps JOIN FETCH ps.product p WHERE p.productId = :productId")
    Optional<ProductStock> findByProductId(@Param("productId") String productId);

    default ProductStock getValidateProductByProductId(String productId){
        return findByProductId(productId).orElseThrow(() -> new NotFoundException("상품 정보를 찾을 수 없습니다."));
    }

    // FETCH JOIN.
    @Query("SELECT ps FROM ProductStock ps JOIN FETCH ps.product p WHERE p.productId IN :productIds")
    List<ProductStock> findProductStocksByProductIds(@Param("productIds") List<String> productIds);

}
