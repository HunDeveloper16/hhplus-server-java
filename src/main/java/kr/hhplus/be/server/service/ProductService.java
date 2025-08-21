package kr.hhplus.be.server.service;

import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.dto.product.ProductResponseDto;
import kr.hhplus.be.server.model.product.ProductStock;
import kr.hhplus.be.server.repository.product.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductStockRepository productStockRepository;

    /**
     * 상품을 상세 조회합니다.
     *
     * @param productId 상품 아이디
     */
    public ProductResponseDto.ProductInfo getProductDetail(String productId) {
        log.info(" ======= 1. 상품을 상세 조회합니다. ======= ");
        ProductStock productStock = productStockRepository.getValidateProductByProductId(productId);

        return ProductResponseDto.ProductInfo.from(productStock);
    }

    public List<ProductStock> getValidateProductStocksByProductIds(List<String> productId) {
        List<ProductStock> stocks = productStockRepository.findProductStocksByProductIds(productId);

        if(stocks==null && stocks.isEmpty()) throw new NotFoundException("재고 정보를 찾을 수 없습니다.");

        return stocks;
    }

}
