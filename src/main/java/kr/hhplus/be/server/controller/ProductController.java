package kr.hhplus.be.server.controller;

import kr.hhplus.be.server.dto.product.ProductResponseDto;
import kr.hhplus.be.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
