package kr.hhplus.be.server.repository.product;

import kr.hhplus.be.server.dto.product.ProductSalesDto;
import kr.hhplus.be.server.model.product.ProductStockHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductStockHistoryRepository extends JpaRepository<ProductStockHistory, Long> {

    /**
     * 최근 가장 많이 팔린 상품 조회
     *
     * @param start 조회 시작 일자
     * @param end 조회 종료 일자
     * @param pageable 페이징 건수
     *
     * @return ProductSalesDto
     */
    @Query(value = "SELECT new kr.hhplus.be.server.dto.product.ProductSalesDto(SUM(p.quantity) AS totalSales, p.productSeqNo) " + // 위치 기반
            "FROM ProductStockHistory p " +
            "WHERE p.regDt BETWEEN :start AND :end " +
            "GROUP BY p.productSeqNo " +
            "ORDER BY SUM(p.quantity) DESC",
            nativeQuery = true)
    List<ProductSalesDto> findProductSalesRankingByPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);


}
