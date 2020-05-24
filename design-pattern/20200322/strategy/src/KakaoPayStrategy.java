package net.mossol.practice.design.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoPayStrategy implements PaymentStrategy {
    private String kakaoId;
    private String password;

    public KakaoPayStrategy(String kakaoId, String password) {
        this.kakaoId = kakaoId;
        this.password = password;
    }

    @Override
    public void pay(int amount) {
        log.info("{} paid by kakao pay", amount);
    }
}
