package kr.hhplus.be.server.clean.interfaces.web;

import kr.hhplus.be.server.clean.application.OrderCommand;
import kr.hhplus.be.server.clean.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 / 결제 API
     *
     * 요구사항
     * 1.사용자 식별자와 (상품 ID, 수량) 목록을 입력받아 주문하고 결제를 수행하는 API 를 작성합니다.
     * 2.결제는 기 충전된 잔액을 기반으로 수행하며 성공할 시 잔액을 차감해야 합니다.
     * 3.데이터 분석을 위해 결제 성공 시에 실시간으로 주문 정보를 데이터 플랫폼에 전송해야 합니다.( 데이터 플랫폼이 어플리케이션 외부 라는 가정만 지켜 작업해 주시면 됩니다 )
     *
     * @param order 주문 정보
     */
    @PostMapping("/payment")
    public ResponseEntity<?> orderPayment(@RequestBody OrderRequestDto.Order order) {
        // DTO → Command 변환
        OrderCommand command = OrderCommand.from(order);

        // 웹 계층 검증
        order.validateOrderItem();

        // 서비스 로직 command 전달
        orderService.order(command);

        return ResponseEntity.ok().build();
    }

}
