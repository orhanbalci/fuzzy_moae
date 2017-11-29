package net.orhanbalci.fuzzymoea.problem;

import net.orhanbalci.fuzzymoea.db.Db;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

public class DietProblem extends AbstractBinaryProblem
    implements ConstrainedProblem<BinarySolution> {

  private int age;
  private String gender;
  private Db db = new Db();

  public DietProblem() {
    this(5, "child");
  }

  public DietProblem(int age, String gender) {
    setName("Diet Problem");
    setNumberOfConstraints(2);
    setNumberOfObjectives(2);
    setNumberOfVariables(db.getFoods().size());
    this.age = age;
    this.gender = gender;
  }

  @Override
  public void evaluate(BinarySolution solution) {
    double[] fx = new double[getNumberOfObjectives()];
    boolean[] x = new boolean[getNumberOfVariables()];
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      x[i] = solution.getVariableValue(i).get(0);
    }

    int foodCount = 0;
    int sumCost = 0;
    int sumPreference = 0;
    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
      if (x[i]) {
        foodCount++;
        sumCost += db.getFood(i + 1).getCost();
        sumPreference += db.getFood(i + 1).getPreference();
      }
    }

    fx[0] = (double) sumPreference / (double) foodCount;
    fx[1] = (double) sumCost / (double) foodCount;

    solution.setObjective(0, -fx[0]); //maximize preference NSGAII assumes minimization
    solution.setObjective(1, fx[1]); //minimize avg cost
  }

  @Override
  public int getBitsPerVariable(int index) {
    return 1;
  }

  @Override
  public void evaluateConstraints(BinarySolution solution) {}
}
