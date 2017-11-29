package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "food_nutrients")
public class FoodNutrient {
  @DatabaseField(columnName = "Id", id = true)
  private int id;

  @DatabaseField(columnName = "foodId", canBeNull = false, foreign = true)
  private Food food;

  @DatabaseField(columnName = "nutrientId", canBeNull = false, foreign = true)
  private Nutrient nutrient;

  @DatabaseField(columnName = "quantity")
  private float quantity;

  public FoodNutrient() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Food getFood() {
    return food;
  }

  public void setFood(Food food) {
    this.food = food;
  }

  public Nutrient getNutrient() {
    return nutrient;
  }

  public void setNutrient(Nutrient nutrient) {
    this.nutrient = nutrient;
  }

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }
}
