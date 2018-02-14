package net.orhanbalci.fuzzymoea.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.orhanbalci.fuzzymoea.db.*;
import net.orhanbalci.fuzzymoea.entity.*;
import net.orhanbalci.fuzzymoea.problem.*;
import org.uma.jmetal.solution.*;

public class SolutionPrinter {

  private Db db;
  private ConstraintCalculator cc;

  public SolutionPrinter() {
    this.db = new Db();
    cc = new ConstraintCalculator(db, db.getConstraints("child", 5));
  }

  public List<Integer> convertSolution(Solution<Integer> solution) {
    List<Integer> result = new ArrayList<Integer>();
    double ideal = 0.0;
    cc.clear();
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      int foodId = solution.getVariableValue(i) + 1;
      if (cc.shouldBeAdded(db.getFood(foodId))) {
        cc.addFood(db.getFood(foodId));
        result.add(foodId);
      }
    }
    return result;
  }

  public Map<Integer, Float> getNutrientComposition() {
    return cc.getNutrientComposition();
  }

  public Map<Integer, ConstraintResult> getConstraintResult() {
    return cc.getConstraintResult();
  }

  public void writeSolution(String fileName, List<PermutationSolution<Integer>> solution) {
    for (PermutationSolution s : solution) {
      writeSolution(fileName, convertSolution(s), getNutrientComposition(), getConstraintResult());
    }
  }

  public void writeSolution(
      String fileName,
      List<Integer> solution,
      Map<Integer, Float> nutrientComposition,
      Map<Integer, ConstraintResult> constraints) {

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
      bw.write("---------------------------\n");
      bw.write(
          String.join(
              ",", solution.stream().map(s -> String.valueOf(s)).collect(Collectors.toList())));
      bw.write("\n\n");

      for (Integer foodId : solution) {
        Food f = db.getFood(foodId);
        bw.write(f.getName());
        bw.write("\n");
      }
      bw.write("\n\n");

      for (Map.Entry<Integer, Float> nutrientComp : nutrientComposition.entrySet()) {
        Nutrient nutrient = db.getNutrient(nutrientComp.getKey());
        bw.write(nutrient.getName() + " : " + nutrientComp.getValue());
        bw.write("\n");
      }
      bw.write("\n\n");

      for (Map.Entry<Integer, ConstraintResult> constraintEntry : constraints.entrySet()) {
        Nutrient nutrient = db.getNutrient(constraintEntry.getKey());
        bw.write(
            nutrient.getName()
                + " : "
                + constraintEntry.getValue().getResultCode().name()
                + " : "
                + String.valueOf(constraintEntry.getValue().getViolationAmount()));
        bw.write("\n");
      }

      bw.write("\n\n");

      bw.write("---------------------------\n");

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
