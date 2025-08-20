package praktikum;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBuns_setsBunOnBurger() {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Флюоресцентная булка");
        when(bun.getPrice()).thenReturn(50.0f);

        burger.setBuns(bun);

        String receipt = burger.getReceipt();
        assertTrue(receipt.startsWith("(==== Флюоресцентная булка ====)"));
    }

    @Test
    public void addIngredient_appendsToIngredientsList() {
        Ingredient ing1 = mock(Ingredient.class);
        Ingredient ing2 = mock(Ingredient.class);

        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        assertEquals(2, burger.ingredients.size());
        assertSame(ing1, burger.ingredients.get(0));
        assertSame(ing2, burger.ingredients.get(1));
    }

    @Test
    public void removeIngredient_removesByIndex() {
        Ingredient ing1 = mock(Ingredient.class);
        Ingredient ing2 = mock(Ingredient.class);
        Ingredient ing3 = mock(Ingredient.class);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);
        burger.addIngredient(ing3);

        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
        assertSame(ing1, burger.ingredients.get(0));
        assertSame(ing3, burger.ingredients.get(1));
        assertFalse(burger.ingredients.contains(ing2));
    }

    @Test
    public void moveIngredient_movesElementToNewIndex() {
        Ingredient a = mock(Ingredient.class);
        Ingredient b = mock(Ingredient.class);
        Ingredient c = mock(Ingredient.class);
        burger.addIngredient(a);
        burger.addIngredient(b);
        burger.addIngredient(c);

        burger.moveIngredient(0, 2);

        assertSame(b, burger.ingredients.get(0));
        assertSame(c, burger.ingredients.get(1));
        assertSame(a, burger.ingredients.get(2));
    }

    @Test
    public void getPrice_returnsSumOfBunsTwicePlusIngredients() {
        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(100.0f);
        burger.setBuns(bun);

        Ingredient ing1 = mock(Ingredient.class);
        Ingredient ing2 = mock(Ingredient.class);
        when(ing1.getPrice()).thenReturn(15.5f);
        when(ing2.getPrice()).thenReturn(24.5f);
        burger.addIngredient(ing1);
        burger.addIngredient(ing2);

        float actual = burger.getPrice();
        float expected = 100.0f * 2 + 15.5f + 24.5f;

        assertEquals(expected, actual, 0.0001f);
    }

    @Test
    public void getReceipt_returnsFormattedReceipt() {
        Bun bun = mock(Bun.class);
        when(bun.getName()).thenReturn("Люминесцентная булка");
        when(bun.getPrice()).thenReturn(50.0f);
        burger.setBuns(bun);

        Ingredient sauce = mock(Ingredient.class);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("Space Sauce");
        when(sauce.getPrice()).thenReturn(10.0f);

        Ingredient filling = mock(Ingredient.class);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("Метеоритное мясо");
        when(filling.getPrice()).thenReturn(25.0f);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== Люминесцентная булка ====)"));
        assertTrue(receipt.contains("= sauce Space Sauce ="));
        assertTrue(receipt.contains("= filling Метеоритное мясо ="));
        assertTrue(receipt.contains("Price: "));
    }
}
