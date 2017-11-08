package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "foods")
public class Food {
  @DatabaseField(columnName = "id", id = true)
  private int id;

  @DatabaseField(columnName = "name")
  private String name;

  @DatabaseField(columnName = "foodGroupId")
  private int foodGroupId;

  @DatabaseField(columnName = "cost")
  private float cost;

  @DatabaseField(columnName = "preference")
  private int preference;

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

  public int getFoodGroupId() {
    return foodGroupId;
  }

  public void setFoodGroupId(int foodGroupId) {
    this.foodGroupId = foodGroupId;
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
}
