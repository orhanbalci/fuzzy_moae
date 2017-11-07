package net.orhanbalci.fuzzymoea.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "dri")
public class Constraint {
  @DatabaseField(columnName = "id", id = true)
  private int id;

  @DatabaseField(columnName = "nutrient_id")
  private int nutrientId;

  @DatabaseField(columnName = "low_age")
  private int ageLowerBound;

  @DatabaseField(columnName = "up_age")
  private int ageUpperBound;

  @DatabaseField(columnName = "gender")
  private String gender;

  @DatabaseField(columnName = "RLL")
  private float constraintLowerBound;

  @DatabaseField(columnName = "RUL")
  private float constraintUpperBound;

  public Constraint() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNutrientId() {
    return nutrientId;
  }

  public void setNutrientId(int nutrientId) {
    this.nutrientId = nutrientId;
  }

  public int getAgeLowerBound() {
    return ageLowerBound;
  }

  public void setAgeLowerBound(int ageLowerBound) {
    this.ageLowerBound = ageLowerBound;
  }

  public int getAgeUpperBound() {
    return ageUpperBound;
  }

  public void setAgeUpperBound(int ageUpperBound) {
    this.ageUpperBound = ageUpperBound;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public float getConstraintLowerBound() {
    return constraintLowerBound;
  }

  public void setConstraintLowerBound(float constraintLowerBound) {
    this.constraintLowerBound = constraintLowerBound;
  }

  public float getConstraintUpperBound() {
    return constraintUpperBound;
  }

  public void setConstraintUpperBound() {
    this.constraintUpperBound = constraintUpperBound;
  }
}
