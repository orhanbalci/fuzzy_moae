package net.orhanbalci.fuzzymoea.problem;

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
    double ideal = 0.0;
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      if (cc.isConstraintsSatisfied(cc.addFood(db.getFood(solution.getVariableValue(i) + 1)))) {
        foodCount++;
        sumCost += db.getFood(i + 1).getCost();
        ideal +=
            fc.calculateIdeal(
                db.getFood(i + 1).getPreference(),
                db.getFood(i + 1).getPreparationTime(),
                db.getFood(i + 1).getRating());
        //sumPreference += db.getFood(i + 1).getPreference();
      } else {
        cc.removeFood(db.getFood(solution.getVariableValue(i)));
      }
    }

    //fx[0] = (double) sumPreference / (double) foodCount;
    fx[1] = (double) sumCost / (double) foodCount;
    fx[0] = ideal;
    solution.setObjective(0, -fx[0]); //maximize ideal NSGAII assumes minimization
    solution.setObjective(1, fx[1]); //minimize avg cost
  }

  @Override
  public int getPermutationLength() {
    return getNumberOfVariables();
  }
}
