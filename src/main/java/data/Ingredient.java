package data;

public class Ingredient {
    private String name;
    private String id;
    private IngredientType type;

    public Ingredient(String name, String id, IngredientType type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientType getType() {
        return type;
    }
}
