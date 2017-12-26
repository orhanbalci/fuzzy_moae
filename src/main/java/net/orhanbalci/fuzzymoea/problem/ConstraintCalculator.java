package net.orhanbalci.fuzzymoea.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.orhanbalci.fuzzymoea.db.Db;
import net.orhanbalci.fuzzymoea.entity.*;
import net.orhanbalci.fuzzymoea.util.Range;

public class ConstraintCalculator {

  private List<Constraint> constraints = new ArrayList();
  //nutrient id amount
  private HashMap<Integer, Float> nutrientComposition = new HashMap();

  private Db db;

  public ConstraintCalculator(Db db, List<Constraint> constraints) {
    this.constraints = constraints;
    this.db = db;
  }

  public Map<Integer, ConstraintResult> addFood(Food f) {
    Map<Integer, ConstraintResult> result = new HashMap<Integer, ConstraintResult>(10);
    List<FoodNutrient> nutrients = db.getFoodNutrients(f);
    for (FoodNutrient fn : nutrients) {
      int nutrientId = fn.getNutrient().getId();
      if (nutrientComposition.keySet().contains(nutrientId)) {
        nutrientComposition.put(nutrientId, nutrientComposition.get(nutrientId) + fn.getQuantity());
      } else {
        nutrientComposition.put(nutrientId, fn.getQuantity());
      }
      result.put(nutrientId, checkViolation(nutrientId));
    }

    return result;
  }

  public Map<Integer, ConstraintResult> removeFood(Food f) {
    Map<Integer, ConstraintResult> result = new HashMap<Integer, ConstraintResult>(10);
    List<FoodNutrient> nutrients = db.getFoodNutrients(f);
    for (FoodNutrient fn : nutrients) {
      int nutrientId = fn.getNutrient().getId();
      if (nutrientComposition.keySet().contains(nutrientId)) {
        nutrientComposition.put(nutrientId, nutrientComposition.get(nutrientId) - fn.getQuantity());
      }
      result.put(nutrientId, checkViolation(nutrientId));
    }
    return result;
  }

  // public List<ConstraintResult> getConstraintResult(){

  // }

  private ConstraintResult checkViolation(int nutrientId) {
    Constraint cons = getConstraint(nutrientId);
    if (null != cons) {
      Range<Float> r =
          new Range(
              Range.RangeCheck.INCLUDE_NONE,
              cons.getConstraintLowerBound(),
              cons.getConstraintUpperBound());
      if (r.inRange(nutrientComposition.get(nutrientId))) {
        return new ConstraintResult(ConstraintResultCode.WITHINLIMITS, 0.0f);
      } else if (nutrientComposition.get(nutrientId) > r.upper) {
        return new ConstraintResult(
            ConstraintResultCode.OVERDOSE, nutrientComposition.get(nutrientId) - r.upper);
      } else {
        return new ConstraintResult(
            ConstraintResultCode.UNDERDOSE, r.lower - nutrientComposition.get(nutrientId));
      }
    }

    return new ConstraintResult(ConstraintResultCode.WITHINLIMITS, 0.0f);
  }

  private Constraint getConstraint(int nutrientId) {
    return constraints
        .stream()
        .filter(cons -> cons.getNutrient().getId() == nutrientId)
        .findAny()
        .orElse(null);
  }

  public boolean isConstraintsSatisfied(Map<Integer, ConstraintResult> constraints) {
    return constraints
        .entrySet()
        .stream()
        .allMatch(entry -> entry.getValue().getResultCode() == ConstraintResultCode.WITHINLIMITS);
  }
}
