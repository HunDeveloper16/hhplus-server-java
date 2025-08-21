package kr.hhplus.be.server.service;

import kr.hhplus.be.server.dto.order.OrderRequestDto;
import kr.hhplus.be.server.model.product.ProductStock;
import kr.hhplus.be.server.model.user.UserBalance;
import kr.hhplus.be.server.repository.product.ProductStockRepository;
import kr.hhplus.be.server.repository.user.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserBalanceRepository userBalanceRepository;
    private final ProductStockRepository productStockRepository;

    private final ProductService productService;

    /**
     * 주문 결제를 진행합니다.
     *
     * @param order 주문 정보
     */
    @Transactional
    public void orderPayment(OrderRequestDto.Order order){
        // 1. 회원 잔액 조회
        UserBalance userBalance = userBalanceRepository.getValidateUserBalanceByUserId(order.getUserId());

        // 2. 상품 조회
        List<ProductStock> productStocks = productService.getValidateProductStocksByProductIds(order.getProductIdList());

        // 3. 상품 검증
        Long totalAmount = 0L;
        for(ProductStock productStock : productStocks){
            productStock.validateProductStatus(); // 상태 검증
            totalAmount += productStock.getProduct().getPrice(); // 총합 계산
        }

        for(OrderRequestDto.OrderProduct product : order.getOrderProductList()){
            for(ProductStock productStock : productStocks){
                if(product.getProductId().equals(productStock.getProduct().getProductId())){
                    // 상품 상태 검증
                    productStock.validateProductStatus();

                    // 상품 재고 검증
                    productStock.validateProductStock(product.getQuantity());

                    //
                }
            }

            if(product.getProductId().equals(product.getProductId())) {

            }

        }

        // 3. 재고 조회

        // 4. 재고 검증

        // 5. 결제. 성공시 잔액 차감. 실패시 rollback.

        // 6. 내역 저장.

        // 7. 데이터 분석 - 외부 플랫폼 전송
    }

    public void getProductList(){

    }

}
