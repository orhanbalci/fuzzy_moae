package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "food_nutrients")
public class FoodNutrient {
  @DatabaseField(columnName = "Id", id = true)
  private int id;

  @DatabaseField(columnName = "foodId")
  private int foodId;

  @DatabaseField(columnName = "nutrientId")
  private int nutrientId;

  @DatabaseField(columnName = "quantity")
  private float quantity;

  public FoodNutrient() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getFoodId() {
    return foodId;
  }

  public void setFoodId(int foodId) {
    this.foodId = foodId;
  }

  public int getNutrientId() {
    return nutrientId;
  }

  public void setNutrientId(int nutrientId) {
    this.nutrientId = nutrientId;
  }

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }
}
