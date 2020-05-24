package net.mossol.practice.design.strategy;

import org.junit.Test;

public class StrategyPattern {

    @Test
    public void testStrategyPattern() {
        final ShoppingCart cart = new ShoppingCart();
        final Item item1 = new Item("01", 30000);
        final Item item2 = new Item("02", 40000);

        cart.addItem(item1);
        cart.addItem(item2);

        cart.pay(new CreditCardStrategy("MyCard", "01010", "1123", "20200301"));
        cart.pay(new KakaoPayStrategy("test", "test"));
    }
}
