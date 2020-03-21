package net.mossol.practice.design.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditCardStrategy implements PaymentStrategy {
    private String name;
    private String cardNumber;
    private String cvcNumber;
    private String dateOfExpiry;

    public CreditCardStrategy(String name, String cardNumber, String cvcNumber, String dateOfExpiry) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvcNumber = cvcNumber;
        this.dateOfExpiry = dateOfExpiry;
    }

    @Override
    public void pay(int amount) {
        log.info("{} paid by credit card", amount);
    }
}
