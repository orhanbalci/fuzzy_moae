package net.orhanbalci.fuzzymoea.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.orhanbalci.fuzzymoea.db.Db;
import net.orhanbalci.fuzzymoea.entity.*;

public class ConstraintCalculator {

  private List<Constraint> constraints = new ArrayList();
  //nutrient id amount
  private HashMap<Integer, Float> nutrientComposition = new HashMap();

  private Db db;

  public ConstraintCalculator(Db db, List<Constraint> constraints) {
    this.constraints = constraints;
    this.db = db;
  }

  public void addFood(Food f) {
    List<FoodNutrient> nutrients = db.getFoodNutrients(f);
    for (FoodNutrient fn : nutrients) {
      int nutrientId = fn.getNutrient().getId();
      if (nutrientComposition.keySet().contains(nutrientId)) {
        nutrientComposition.put(nutrientId, nutrientComposition.get(nutrientId) + fn.getQuantity());
      } else {
        nutrientComposition.put(nutrientId, fn.getQuantity());
      }
    }
  }

  public void removeFood(Food f) {
    List<FoodNutrient> nutrients = db.getFoodNutrients(f);
    for (FoodNutrient fn : nutrients) {
      int nutrientId = fn.getNutrient().getId();
      if (nutrientComposition.keySet().contains(nutrientId)) {
        nutrientComposition.put(nutrientId, nutrientComposition.get(nutrientId) - fn.getQuantity());
      }
    }
  }
}
