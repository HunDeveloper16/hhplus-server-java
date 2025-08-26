package kr.hhplus.be.server.service;

import ch.qos.logback.core.util.StringUtil;
import kr.hhplus.be.server.common.enums.StockHistoryType;
import kr.hhplus.be.server.common.exception.NotFoundException;
import kr.hhplus.be.server.dto.common.ProductOrderRequest;
import kr.hhplus.be.server.dto.common.ProductOrderResult;
import kr.hhplus.be.server.dto.product.ProductResponseDto;
import kr.hhplus.be.server.dto.product.ProductSalesDto;
import kr.hhplus.be.server.model.product.Product;
import kr.hhplus.be.server.model.product.ProductStock;
import kr.hhplus.be.server.model.product.ProductStockHistory;
import kr.hhplus.be.server.repository.product.ProductRepository;
import kr.hhplus.be.server.repository.product.ProductStockHistoryRepository;
import kr.hhplus.be.server.repository.product.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductStockRepository productStockRepository;

    private final ProductStockHistoryRepository productStockHistoryRepository;

    /**
     * 상품을 상세 조회합니다.
     *
     * @param productId 상품 아이디
     */
    @Transactional(readOnly = true)
    public ProductResponseDto.ProductInfo getProductDetail(String productId) {
        log.info(" ======= 1. 상품을 상세 조회합니다. ======= ");
        ProductStock productStock = productStockRepository.getValidateProductByProductId(productId);

        return ProductResponseDto.ProductInfo.from(productStock);
    }

    /**
     * 주문 상품들을 처리합니다 (재고 검증, 차감, 총액 계산)
     *
     * @param orderRequest 주문 상품 목록
     * @return 처리 결과
     */
    @Transactional
    public ProductOrderResult processOrderProducts(ProductOrderRequest orderRequest) {
        // 주문 요청 상품 아이디 추출
        List<String> productIds = orderRequest.getOrderProductList().stream()
                .map(ProductOrderRequest.OrderProduct::getProductId)
                .collect(Collectors.toList());

        // 상품 재고 조회. FETCH JOIN
        List<ProductStock> productStocks = getValidateProductStocksByProductIds(productIds);

        long totalAmount = 0L;
        List<ProductStockHistory> stockHistories = new ArrayList<>();
        List<ProductOrderResult.ProductOrderItem> orderItems = new ArrayList<>();

        for (ProductOrderRequest.OrderProduct orderProduct : orderRequest.getOrderProductList()) {
            ProductStock productStock = findProductStock(productStocks, orderProduct.getProductId());

            // 상품 도메인 로직 - 재고 차감 및 검증
            productStock.reduceStockByOrder(orderProduct.getQuantity());

            // 히스토리 생성
            stockHistories.add(ProductStockHistory.builder()
                    .productStock(productStock)
                    .productSeqNo(productStock.getSeqNo())
                    .productName(productStock.getProduct().getName())
                    .quantity(orderProduct.getQuantity())
                    .type(StockHistoryType.ORDER)
                    .orderNo(orderRequest.getOrderNo())
                    .build());

            // 각 아이템별 총액 계산
            totalAmount += productStock.getProduct().getPrice() * orderProduct.getQuantity();

            // 주문 아이템 생성
            orderItems.add(
                    ProductOrderResult.ProductOrderItem.builder()
                            .productStockSeqNo(productStock.getSeqNo())
                            .productName(productStock.getProduct().getName())
                            .quantity(orderProduct.getQuantity())
                            .totalAmount(totalAmount)
                            .build()
            );
        }

        // 재고 정보 저장
        productStockRepository.saveAll(productStocks);

        // 재고 히스토리 저장
        productStockHistoryRepository.saveAll(stockHistories);

        // 주문 상품 결과 return
        return ProductOrderResult.of(orderItems, totalAmount);
    }

    @Transactional(readOnly = true)
    public List<ProductStock> getValidateProductStocksByProductIds(List<String> productId) {
        List<ProductStock> stocks = productStockRepository.findProductStocksByProductIds(productId);

        if(stocks==null && stocks.isEmpty()) throw new NotFoundException("재고 정보를 찾을 수 없습니다.");

        return stocks;
    }

    /**
     * 최근 3일간 가장 많이 팔린 상위 5개의 상품을 조회합니다.
     *
     * @return 상위 5개 상품 목록
     */
    @Transactional(readOnly = true)
    public List<ProductResponseDto.RecentSalesProduct> getRecentSalesTop5Ranking(){
        // 최근 3일간 가장 많이 팔린 5개 상품 조회
        List<ProductSalesDto> result = productStockHistoryRepository.findProductSalesRankingByPeriod(LocalDateTime.now()
                , LocalDateTime.now().minusDays(3)
                , PageRequest.of(0, 5));

        if(result.isEmpty()){
            return Collections.emptyList();
        }

        List<Long> productSeqNos = result.stream().map(ProductSalesDto::getProductSeqNo).toList();

        // 위에서 조회된 상품 일련 번호로 상품 정보 조회
        List<Product> productList = productRepository.findProductByProductSeqNos(productSeqNos);

        // Product 매핑을 빠르게 하기 위해 Map 변환
        Map<Long, ProductSalesDto> salesMap = result.stream()
                .collect(Collectors.toMap(ProductSalesDto::getProductSeqNo, dto -> dto));

        // 상품별 판매량 세팅
        productList.forEach(product -> {
            ProductSalesDto dto = salesMap.get(product.getSeqNo());

            if (dto != null) {
                product.setTotalSales(dto.getTotalSales());
            }
        });

        return productList.stream().map(ProductResponseDto.RecentSalesProduct::from).toList();
    }

    public ProductStock findProductStock(List<ProductStock> productStocks, String productId){
        return productStocks.stream()
                .filter(p -> p.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다: " + productId));
    }


}
