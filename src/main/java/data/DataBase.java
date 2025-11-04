package data;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private final List<Ingredient> buns = new ArrayList<>();
    private final List<Ingredient> ingredients = new ArrayList<>();

   public DataBase() {
       buns.add(new Ingredient("Флюоресцентная булка R2-D3", "61c0c5a71d1f82001bdaaa6d", IngredientType.BUN));
       buns.add(new Ingredient("Краторная булка N-200i", "61c0c5a71d1f82001bdaaa6c", IngredientType.BUN));

       ingredients.add(new Ingredient("Мясо бессмертных моллюсков Protostomia", "61c0c5a71d1f82001bdaaa6f", IngredientType.MAIN));
       ingredients.add(new Ingredient("Говяжий метеорит (отбивная)", "61c0c5a71d1f82001bdaaa70", IngredientType.MAIN));
       ingredients.add(new Ingredient("Биокотлета из марсианской Магнолии", "61c0c5a71d1f82001bdaaa71", IngredientType.MAIN));
       ingredients.add(new Ingredient("Филе Люминесцентного тетраодонтимформа", "61c0c5a71d1f82001bdaaa6e", IngredientType.MAIN));
       ingredients.add(new Ingredient("Хрустящие минеральные кольца", "61c0c5a71d1f82001bdaaa76", IngredientType.MAIN));
       ingredients.add(new Ingredient("Плоды Фалленианского дерева", "61c0c5a71d1f82001bdaaa77", IngredientType.MAIN));
       ingredients.add(new Ingredient("Кристаллы марсианских альфа-сахаридов", "61c0c5a71d1f82001bdaaa78", IngredientType.MAIN));
       ingredients.add(new Ingredient("Мини-салат Экзо-Плантаго", "61c0c5a71d1f82001bdaaa79", IngredientType.MAIN));
       ingredients.add(new Ingredient("Сыр с астероидной плесенью", "61c0c5a71d1f82001bdaaa7a", IngredientType.MAIN));

       ingredients.add(new Ingredient("Соус Spicy-X", "61c0c5a71d1f82001bdaaa72", IngredientType.SAUCE));
       ingredients.add(new Ingredient("Соус фирменный Space Sauce", "61c0c5a71d1f82001bdaaa73", IngredientType.SAUCE));
       ingredients.add(new Ingredient("Соус традиционный галактический", "61c0c5a71d1f82001bdaaa74", IngredientType.SAUCE));
       ingredients.add(new Ingredient("Соус с шипами Антарианского плоскоходца", "61c0c5a71d1f82001bdaaa75", IngredientType.SAUCE));
   }

    public List<Ingredient> availableBuns() {
        return buns;
    }

    public List<Ingredient> availableIngredients() {
        return ingredients;
    }
}
