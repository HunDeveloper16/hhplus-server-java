package kr.hhplus.be.server.clean.application;

import kr.hhplus.be.server.clean.interfaces.web.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCommand {
    private String productId;
    private Long quantity;

    public static OrderItemCommand from(OrderRequestDto.OrderProduct orderProduct){
        return new OrderItemCommand(orderProduct.getProductId(),  orderProduct.getQuantity());
    }
}
