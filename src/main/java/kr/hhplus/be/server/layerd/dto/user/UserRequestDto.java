package kr.hhplus.be.server.layerd.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRequestDto {

    @Getter
    @AllArgsConstructor
    public static class Charge{
        String userId; // 유저 아이디
        Long amount; // 수량
    }

}
