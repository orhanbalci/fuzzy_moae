package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "foods")
public class Food {
  @DatabaseField(columnName = "id", id = true)
  private int id;

  @DatabaseField(columnName = "name")
  private String name;

  @DatabaseField(columnName = "foodGroupId", canBeNull = false, foreign = true)
  private FoodGroup foodGroup;

  @DatabaseField(columnName = "cost")
  private float cost;

  @DatabaseField(columnName = "preference")
  private int preference;

  @DatabaseField(columnName = "preparation_time")
  private int preparationTime;

  @DatabaseField(columnName = "rating")
  private int rating;

  public Food() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FoodGroup getFoodGroup() {
    return foodGroup;
  }

  public void setFoodGroup(FoodGroup foodGroup) {
    this.foodGroup = foodGroup;
  }

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }

  public int getPreference() {
    return preference;
  }

  public void setPreference(int preference) {
    this.preference = preference;
  }

  public int getPreparationTime() {
    return preparationTime;
  }

  public void setPreparationTime(int preparationTime) {
    this.preparationTime = preparationTime;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }
}
