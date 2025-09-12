package kr.hhplus.be.server.common.util;

import java.util.Random;

public class RandomCodeGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    /**
     * 임의로 주문번호를 생성합니다.
     *
     * @return CXQBZL02 ( 영문 6자리 + 숫자 2자리 )
     */
    public static String generateOrderNo(){
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
