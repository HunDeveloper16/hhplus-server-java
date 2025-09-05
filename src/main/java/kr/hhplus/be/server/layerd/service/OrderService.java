package kr.hhplus.be.server.layerd.service;

import kr.hhplus.be.server.common.util.RandomCodeGenerator;
import kr.hhplus.be.server.layerd.dto.common.*;
import kr.hhplus.be.server.layerd.dto.order.OrderRequestDto;
import kr.hhplus.be.server.layerd.model.order.Order;
import kr.hhplus.be.server.layerd.model.order.OrderItem;
import kr.hhplus.be.server.layerd.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService { // 타 서비스에서 호출시 순환참조 가능성.

    private final ProductService productService;
    private final UserService userService;
    private final CouponService couponService;

    private final OrderRepository orderRepository;


    /**
     * 주문 결제를 진행합니다.
     *
     * 쿠폰, 주문, 회원이 각각 다른 모듈에 있다고 생각하고 설계하였습니다.
     * 다만, 실질적으로 모듈 분리가 되지않았으므로 이후 로직 실패시 롤백로직은 작성하지않고 트랜잭션이 합류 되어있는 것을 인지하고있습니다.
     *
     * @param orderRequest 주문 요청 정보
     */
    @Transactional
    public void orderPayment(OrderRequestDto.Order orderRequest){
        log.info(" ======= 요청 값 검증 ======= ");
        orderRequest.validateOrderItem();

        log.info(" ======= 주문 번호 생성 ======= ");
        String orderNo = RandomCodeGenerator.generateOrderNo();

        log.info(" ======= 상품 처리 ======= "); // 재고 업데이트
        ProductOrderResult productOrderResult = productService.processOrderProducts(
                ProductOrderRequest.of(orderNo, orderRequest));

        log.info(" ======= 쿠폰 처리 ======= "); // 할인 처리,쿠폰 사용 처리
        CouponOrderResult couponOrderResult = couponService.applyCouponDiscount(
                CouponOrderRequest.of(orderNo, orderRequest, productOrderResult.getTotalAmount()));

        log.info(" ======= 회원 처리 ======= "); // 잔액 차감 처리
        UserOrderResult userOrderResult = userService.deductBalance(
                UserOrderRequest.of(orderNo, orderRequest, productOrderResult.getTotalAmount()));

        log.info(" ======= 주문 생성 처리 ======= ");
        generateAndSaveOrder(
                orderNo,
                orderRequest,
                productOrderResult,
                couponOrderResult,
                userOrderResult
        );

        // 데이터 분석 - 외부 플랫폼 전송
    }

    /**
     * 주문을 생성하고 저장합니다.
     *
     * @param productOrderResult 상품 주문 결과
     */
    @Transactional
    public void generateAndSaveOrder(String orderNo,
                                     OrderRequestDto.Order orderRequest,
                                     ProductOrderResult productOrderResult,
                                     CouponOrderResult couponOrderResult,
                                     UserOrderResult userOrderResult){

        // 주문 생성
        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(orderRequest.getUserId())
                .totalAmount(couponOrderResult.getTotalAmount())
                .discountAmount(couponOrderResult.getDiscountAmount())
                .regDt(LocalDateTime.now())
                .build();

        // 주문 아이템 생성
        List<OrderItem> orderItems = productOrderResult.getProductOrderItems().stream()
                .map(OrderItem::from)
                .toList();

        // 양방향 매핑
        order.addOrderItems(orderItems);

        // 주문 & 주문 아이템 저장
        orderRepository.save(order);
    }
}
