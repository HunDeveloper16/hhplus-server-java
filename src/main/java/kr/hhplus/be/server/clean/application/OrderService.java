package kr.hhplus.be.server.clean.application;

import kr.hhplus.be.server.clean.domain.model.Order;
import kr.hhplus.be.server.clean.domain.model.OrderItem;
import kr.hhplus.be.server.clean.domain.repository.OrderRepository;
import kr.hhplus.be.server.common.util.RandomCodeGenerator;
import kr.hhplus.be.server.layerd.dto.common.*;
import kr.hhplus.be.server.layerd.service.CouponService;
import kr.hhplus.be.server.layerd.service.ProductService;
import kr.hhplus.be.server.layerd.service.UserService;

import java.util.List;

/**
 * 순수 유스케이스 클래스
 * - @Service, 스프링 의존성 제거
 * - 트랜잭션 경계만 남기고, 프레임워크 * DB 세부 지식 없음
 */
public class OrderService{

    private final OrderRepository orderRepository; // 도메인 포트(인터페이스)

    private final ProductService productService;
    private final UserService userService;
    private final CouponService couponService;

    public OrderService(OrderRepository orderRepository, ProductService productService, UserService userService, CouponService couponService){
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
        this.couponService = couponService;
    }

    @org.springframework.transaction.annotation.Transactional
    public Order order(OrderCommand orderRequest){
        // 주문 번호 생성
        String orderNo = RandomCodeGenerator.generateOrderNo();

        // 상품 처리
        ProductOrderResult productOrderResult = productService.processOrderProducts(ProductOrderRequest.of(orderNo, orderRequest));

        // 재고 업데이트
        CouponOrderResult couponOrderResult = couponService.applyCouponDiscount(CouponOrderRequest.of(orderNo, orderRequest, productOrderResult.getTotalAmount()));

        // 할인 처리. 결과 값은 추후 필요 시 사용
        UserOrderResult userOrderResult = userService.deductBalance(UserOrderRequest.of(orderNo, orderRequest, productOrderResult.getTotalAmount()));

        // 도메인 생성(주문 아이템)
        List<OrderItem> orderItems = productOrderResult.getProductOrderItems().stream()
                .map(p -> OrderItem.create(p.getProductStockSeqNo(), orderNo,  p.getProductName(), p.getQuantity(), p.getTotalAmount()))
                .toList();

        // 도메인 생성(주문)
        Order order = Order.create(orderNo, orderRequest.getUserId(),couponOrderResult.getTotalAmount(), couponOrderResult.getDiscountAmount(), orderItems);

        // 도메인 -> 엔티티 변환 및 db 저장 처리
        orderRepository.save(order);

        return order;
    }

}
