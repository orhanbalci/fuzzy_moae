package net.orhanbalci.fuzzymoea.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.orhanbalci.fuzzymoea.db.Db;
import net.orhanbalci.fuzzymoea.fuzzy.*;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;

public class DietProblemPermutation extends AbstractIntegerPermutationProblem {

  private int age;
  private String gender;
  private Db db = new Db();
  private int numberOfConstraints = 0;
  private ConstraintCalculator cc;
  private FuzzyCalculator fc;
  private static int iterationCount = 0;
  private static final boolean DEBUG = true;
  private List<Float> iterationEpsilon = new ArrayList<Float>();

  public DietProblemPermutation() {
    this(5, "child");
  }

  public DietProblemPermutation(int age, String gender) {
    numberOfConstraints = db.getConstraints(gender, age).size();
    setName("Diet Problem");
    setNumberOfConstraints(numberOfConstraints);
    setNumberOfObjectives(2);
    setNumberOfVariables(db.getFoods().size());
    this.age = age;
    this.gender = gender;
    cc = new ConstraintCalculator(db, db.getConstraints(gender, age));
    fc = new FuzzyCalculator("diet.fcl");
    iterationEpsilon = generateConstraintEpsilonValues(5.0f, 1000);
  }

  // @Override
  // public int getNumberOfConstraints() {
  //   return numberOfConstraints;
  // }

  // @Override
  // public void evaluateConstraints(PermutationSolution<Integer> solution) {}

  @Override
  public void evaluate(PermutationSolution<Integer> solution) {
    //solution.ge
    double[] fx = new double[getNumberOfObjectives()];

    int foodCount = 0;
    int sumCost = 0;
    int sumPreference = 0;
    int sumPreperationTime = 0;
    int sumRating = 0;

    double ideal = 0.0;
    cc.clear();
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      int foodId = solution.getVariableValue(i) + 1;
      if (cc.isConstraintsSatisfied(cc.addFood(db.getFood(foodId)))) {
        foodCount++;
        sumCost += db.getFood(foodId).getCost();
        sumPreference += db.getFood(foodId).getPreference();
        sumPreperationTime += db.getFood(foodId).getPreparationTime();
        sumRating += db.getFood(foodId).getRating();
        // ideal +=
        //     fc.calculateIdeal(
        //         db.getFood(foodId).getPreference(),
        //         db.getFood(foodId).getPreparationTime(),
        //         db.getFood(foodId).getRating());
        //sumPreference += db.getFood(i + 1).getPreference();
        if (DEBUG) {
          System.out.println("************");
          System.out.format("Added food %d\n", foodId);
          System.out.println("************");
        }

      } else {
        if (DEBUG) {
          System.out.println("-----------");
          cc.printFirstUnsatisfiedConstraint();
          System.out.format("Removed food %d\n", foodId);
          System.out.println("-----------");
        }
        cc.removeFood(db.getFood(foodId));
      }
    }

    //fx[0] = (double) sumPreference / (double) foodCount;
    fx[1] = (double) sumCost / (double) foodCount;
    fx[0] =
        fc.calculateIdeal(
            sumPreference / foodCount, sumPreperationTime / foodCount, sumRating / foodCount);
    solution.setObjective(0, -fx[0]); //maximize ideal NSGAII assumes minimization
    solution.setObjective(1, fx[1]); //minimize avg cost

    if (DEBUG) {
      System.out.format("Iteration Count %d\n", iterationCount++);
      System.out.format("Average cost objective %f\n", fx[1]);
      System.out.format("Ideal objective %f\n", fx[0]);
      System.out.println();
    }
  }

  @Override
  public int getPermutationLength() {
    return getNumberOfVariables();
  }

  private List<Float> generateConstraintEpsilonValues(float seed, int numberOfIterations) {
    float iterationEpsilon = seed / (float) numberOfIterations;
    return Stream.iterate(seed, i -> i - iterationEpsilon)
        .limit(numberOfIterations)
        .collect(Collectors.toList());
  }
}
