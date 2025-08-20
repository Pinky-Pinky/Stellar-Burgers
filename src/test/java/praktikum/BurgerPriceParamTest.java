package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BurgerPriceParamTest {

    @Parameterized.Parameters(name = "bun={0}, ingredients={1} => expected={2}")
    public static Object[][] data() {
        return new Object[][]{
                {0f, Arrays.asList(), 0f},
                {100f, Arrays.asList(0f), 200f},
                {123.45f, Arrays.asList(1f, 2.5f, 3f), 123.45f * 2 + 1f + 2.5f + 3f}
        };
    }

    private final float bunPrice;
    private final List<Float> ingredientPrices;
    private final float expected;

    public BurgerPriceParamTest(float bunPrice, List<Float> ingredientPrices, float expected) {
        this.bunPrice = bunPrice;
        this.ingredientPrices = ingredientPrices;
        this.expected = expected;
    }

    @Test
    public void price_isCalculatedCorrectly_forVariousInputs() {
        // arrange
        Burger burger = new Burger();

        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(bunPrice);
        burger.setBuns(bun);

        for (Float p : ingredientPrices) {
            Ingredient ingredient = mock(Ingredient.class);
            when(ingredient.getPrice()).thenReturn(p);
            burger.addIngredient(ingredient);
        }

        // act + assert
        assertEquals(expected, burger.getPrice(), 0.0001f);
    }
}
