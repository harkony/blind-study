package net.mossol.practice.design;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

// GOF의 빌더패턴
@Slf4j
@RunWith(JUnit4.class)
public class GoFBuilderPattern {

    // 복합 객체의 표현형 (Representative)
    @Setter
    @ToString
    private static class Coffee {
        private String cream;
        private String size;
        private int water;
        private int shot;
        private int milk;
    }

    // Builder
    private abstract class CoffeeBuilder {
        protected Coffee coffee;

        public Coffee getCoffee() {
            return coffee;
        }

        public void createCoffee() {
            coffee = new Coffee();
        }

        public abstract void buildCream();
        public abstract void buildSize();
        public abstract void buildWater();
        public abstract void buildShot();
        public abstract void buildMilk();
    }

    // Concrete Builder
    private class AmericanoBuilder extends CoffeeBuilder {
        @Override
        public void buildCream() {
            coffee.setCream("None");
        }

        @Override
        public void buildSize() {
            coffee.setSize("Tall");
        }

        @Override
        public void buildWater() {
            coffee.setWater(100);
        }

        @Override
        public void buildShot() {
            coffee.setShot(2);
        }

        @Override
        public void buildMilk() {
            coffee.setMilk(0);
        }
    }

    private class LatteBuilder extends CoffeeBuilder {
        @Override
        public void buildCream() {
            coffee.setCream("None");
        }

        @Override
        public void buildSize() {
            coffee.setSize("Tall");
        }

        @Override
        public void buildWater() {
            coffee.setWater(80);
        }

        @Override
        public void buildShot() {
            coffee.setShot(2);
        }

        @Override
        public void buildMilk() {
            coffee.setMilk(1);
        }
    }

    // Director
    private static class Cafe {
        private final CoffeeBuilder coffeeBuilder;

        Cafe(CoffeeBuilder coffeeBuilder) {
            this.coffeeBuilder = coffeeBuilder;
        }

        public void createCoffee() {
            coffeeBuilder.createCoffee();
            coffeeBuilder.buildCream();
            coffeeBuilder.buildMilk();
            coffeeBuilder.buildShot();
            coffeeBuilder.buildSize();
            coffeeBuilder.buildWater();
        }

        public Coffee getCoffee() {
            return coffeeBuilder.getCoffee();
        }
    }

    // Client
    @Test
    public void testBuilderPattern() {
        CoffeeBuilder coffeeBuilder = new AmericanoBuilder();
        Cafe cafe = new Cafe(coffeeBuilder);
        cafe.createCoffee();

        Coffee coffee = cafe.getCoffee();
        log.debug("{}", coffee);

        coffeeBuilder = new LatteBuilder();
        cafe = new Cafe(coffeeBuilder);
        cafe.createCoffee();

        coffee = cafe.getCoffee();
        log.debug("{}", coffee);
    }
}
