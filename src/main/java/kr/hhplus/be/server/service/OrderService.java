package kr.hhplus.be.server.service;

import kr.hhplus.be.server.dto.common.ProductOrderResult;
import kr.hhplus.be.server.dto.order.OrderRequestDto;
import kr.hhplus.be.server.model.order.Order;
import kr.hhplus.be.server.model.order.OrderItem;
import kr.hhplus.be.server.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService { // 타 서비스에서 호출시 순환참조 가능성.

    private final ProductService productService;
    private final UserService userService;

    private final OrderRepository orderRepository;

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    /**
     * 주문 결제를 진행합니다.
     *
     * 주문과 회원이 각각 다른 모듈에 있다고 생각하고 설계하였습니다.
     * 다만, 실질적으로 모듈 분리가 되지않았으므로 이후 로직 실패시 롤백로직은 작성하지않고 트랜잭션이 합류 되어있는 것을 인지하고있습니다.
     *
     * @param orderRequest 주문 요청 정보
     */
    @Transactional
    public void orderPayment(OrderRequestDto.Order orderRequest){
        log.info(" ======= 요청 값 검증 ======= ");
        orderRequest.validateOrderItem();

        log.info(" ======= 주문 상품 처리 ======= ");
        ProductOrderResult productOrderResult = productService.processOrderProducts(orderRequest.getOrderProductList());

        log.info(" ======= 회원 잔액 처리 ======= ");
        userService.deductBalance(orderRequest.getUserId(), productOrderResult.getTotalAmount());

        log.info(" ======= 주문 생성 처리 ======= ");
        generateAndSaveOrder(productOrderResult);

        // 데이터 분석 - 외부 플랫폼 전송
    }

    /**
     * 주문을 생성하고 저장합니다.
     *
     * @param productOrderResult 상품 주문 결과
     */
    @Transactional
    public void generateAndSaveOrder(ProductOrderResult productOrderResult){
        Order order = Order.of(generateOrderNo(), productOrderResult.getTotalAmount());

        List<OrderItem> orderItems = productOrderResult.getProductOrderItems().stream()
                .map(OrderItem::from)
                .toList();
        order.addOrderItems(orderItems);

        orderRepository.save(order);
    }



    /**
     * 임의로 주문번호를 생성합니다.
     *
     * @return CXQBZL02 ( 영문 6자리 + 숫자 2자리 )
     */
    public String generateOrderNo(){
        StringBuilder orderNumber = new StringBuilder();

        // 영문 6자리 생성
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(LETTERS.length());
            orderNumber.append(LETTERS.charAt(index));
        }

        // 숫자 2자리 생성 (00-99)
        int number = random.nextInt(100);
        orderNumber.append(String.format("%02d", number));

        return orderNumber.toString();
    }
}
