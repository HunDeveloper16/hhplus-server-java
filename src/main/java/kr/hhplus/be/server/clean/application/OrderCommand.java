package kr.hhplus.be.server.clean.application;

import kr.hhplus.be.server.clean.interfaces.web.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCommand {
    private String userId;
    private String couponId;
    private List<OrderItemCommand> orderProductList;

    public static OrderCommand from(OrderRequestDto.Order dto) {
        return OrderCommand.builder()
                .userId(dto.getUserId())
                .couponId(dto.getCouponId())
                .orderProductList(dto.getOrderProductList().stream().map(OrderItemCommand::from).toList())
                .build();
    }
}
