package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nutrients")
public class Nutrient {
  @DatabaseField(columnName = "id")
  private int id;

  @DatabaseField(columnName = "name")
  private String name;

  @DatabaseField(columnName = "nGroupId")
  private int nutrientGroupId;

  @DatabaseField(columnName = "unitId")
  private int unitId;

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

  public int getNutrientGroupId() {
    return nutrientGroupId;
  }

  public void setNutrientGroupId(int nutrientGroupId) {
    this.nutrientGroupId = nutrientGroupId;
  }

  public int getUnitId() {
    return unitId;
  }

  public void setUnitId(int unitId) {
    this.unitId = unitId;
  }
}
