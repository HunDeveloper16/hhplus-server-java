package kr.hhplus.be.server.layerd.controller;

import kr.hhplus.be.server.layerd.dto.product.ProductResponseDto;
import kr.hhplus.be.server.layerd.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 상세 조회 API
     *
     * 요구사항
     * 1.상품 정보(ID, 이름, 가격, 잔여 수량) 필요
     * 2.조회시점의 상품별 잔여수량이 정확할수록 좋습니다.
     *
     * @param productId 상품 아이디
     * @return 상품 정보 DTO
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto.ProductInfo> getBalance(@PathVariable String productId) {

        return ResponseEntity.ok(productService.getProductDetail(productId));
    }

    /**
     * 상위 상품 조회 API
     *
     * 요구사항
     * 1. 최근 3일간 가장 많이 팔린 상위 5개 상품 정보를 제공하는 API 를 작성합니다.
     * 2. 통계 정보를 다루기 위한 기술적 고민을 충분히 해보도록 합니다.
     *
     * @return 최근 3일간 가장 많이 팔린 상위 5개 상품 정보
     */
    @GetMapping("/recent/sales")
    public ResponseEntity<List<ProductResponseDto.RecentSalesProduct>> getRecentSales() {

        return ResponseEntity.ok(productService.getRecentSalesTop5Ranking());
    }

}
