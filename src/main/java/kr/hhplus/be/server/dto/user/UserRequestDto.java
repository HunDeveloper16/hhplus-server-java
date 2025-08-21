package kr.hhplus.be.server.dto.user;

import lombok.Getter;

public class UserRequestDto {

    @Getter
    public static class Charge{
        String userId; // 유저 아이디
        Long amount; // 수량
    }

}
