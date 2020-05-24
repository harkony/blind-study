package net.mossol.practice.design;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(JUnit4.class)
public class BuilderPattern {

    public class NutritionFactsWithNoBuilder {
        private final int servingSize;  // 필수
        private final int servings;     // 필수
        private final int calories;     // 선택
        private final int fat;          // 선택
        private final int sodium;       // 선택
        private final int carbohydrate; // 선택

        public NutritionFactsWithNoBuilder(int servingSize, int servings, int calories) {
            this(servingSize, servings, calories, 0, 0, 0);
        }

        public NutritionFactsWithNoBuilder(int servingSize, int servings, int calories, int fat) {
            this(servingSize, servings, calories, fat, 0, 0);
        }

        public NutritionFactsWithNoBuilder(int servingSize, int servings, int calories, int fat, int sodium) {
            this(servingSize, servings, calories, fat, sodium, 0);
        }

        public NutritionFactsWithNoBuilder(int servingSize, int servings, int calories, int fat, int sodium,
                                           int carbohydrate) {
            this.servingSize = servingSize;
            this.servings = servings;
            this.calories = calories;
            this.fat = fat;
            this.sodium = sodium;
            this.carbohydrate = carbohydrate;
        }
    }

    @Test
    public void testBuilderPattern() {
        NutritionFacts nutritionFacts =
                new NutritionFacts.Builder(1, 2)
                .calories(100).sodium(35).build();
    }
}
