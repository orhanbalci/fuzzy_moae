package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "food_group")
public class FoodGroup {
  @DatabaseField(columnName = "id", id = true)
  private int id;

  @DatabaseField(columnName = "name")
  private String name;

  public FoodGroup() {}

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
}
