package net.orhanbalci.fuzzymoea.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.orhanbalci.fuzzymoea.db.*;
import net.orhanbalci.fuzzymoea.entity.*;
import net.orhanbalci.fuzzymoea.problem.*;
import org.uma.jmetal.solution.*;

public class SolutionPrinter {

  private Db db;
  private ConstraintCalculator cc;

  public SolutionPrinter(String gender, int age) {
    this.db = new Db();
    cc = new ConstraintCalculator(db, db.getConstraints(gender, age));
  }

  public void convertSolution(String inputFile, String outputFile, String timeFile) {
    List<List<Integer>> solutions = getSolutions(inputFile);
    for (List<Integer> s : solutions) {
      List<Integer> converted = convertSolution(s);
      writeSolution(outputFile, converted, getNutrientComposition(), getConstraintResult());
      writeTimeStatistics(timeFile, converted, getNutrientComposition(), getConstraintResult());
    }
  }

  public List<List<Integer>> getSolutions(String inputFile) {
    try (Stream<String> lines = Files.lines(Paths.get(inputFile))) {
      return lines
          .map(
              line ->
                  Arrays.asList(line.split(" "))
                      .stream()
                      .map(sline -> Integer.parseInt(sline))
                      .collect(Collectors.toList()))
          .collect(Collectors.toList());
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return Collections.<List<Integer>>emptyList();
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

  public List<Integer> convertSolution(List<Integer> solution) {
    List<Integer> result = new ArrayList<Integer>();
    double ideal = 0.0;
    cc.clear();
    for (int i = 0; i < solution.size(); i++) {
      int foodId = solution.get(i) + 1;
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

  private void writeTimeStatistics(
      String fileName,
      List<Integer> solution,
      Map<Integer, Float> nutrientComposition,
      Map<Integer, ConstraintResult> constraints) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
      int totalPreperationTime = 0;
      for (Integer foodId : solution) {
        Food f = db.getFood(foodId);
        totalPreperationTime += f.getPreparationTime();
      }

      bw.write(String.valueOf(totalPreperationTime));
      bw.write(" ");
      bw.write(String.valueOf((double) totalPreperationTime / (double) solution.size()));
      bw.write("\n");

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
