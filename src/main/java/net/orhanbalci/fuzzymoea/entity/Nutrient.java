package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nutrients")
public class Nutrient {
  @DatabaseField(columnName = "id", id = true)
  private int id;

  @DatabaseField(columnName = "name")
  private String name;

  @DatabaseField(columnName = "nGroupId", canBeNull = false, foreign = true)
  private NutrientGroup nutrientGroup;

  @DatabaseField(columnName = "unitId", canBeNull = false, foreign = true)
  private Unit unit;

  public Nutrient() {}

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

  public NutrientGroup getNutrientGroup() {
    return nutrientGroup;
  }

  public void setNutrientGroup(NutrientGroup nutrientGroup) {
    this.nutrientGroup = nutrientGroup;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }
}
