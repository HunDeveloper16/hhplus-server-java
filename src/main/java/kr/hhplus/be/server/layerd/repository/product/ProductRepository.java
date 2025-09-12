package kr.hhplus.be.server.layerd.repository.product;

import kr.hhplus.be.server.layerd.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(String productId);


    /**
     * 상품 일련번호 IN 쿼리
     *
     * @param productSeqNos 상품 일련 번호 목록
     */
    @Query("SELECT p FROM Product p WHERE p.seqNo IN :productSeqNos")
    List<Product> findProductByProductSeqNos(@Param("productSeqNos") List<Long> productSeqNos);

}
