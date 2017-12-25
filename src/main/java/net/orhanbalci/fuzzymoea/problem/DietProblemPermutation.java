package net.orhanbalci.fuzzymoea.problem;

import net.orhanbalci.fuzzymoea.db.Db;
import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;

public class DietProblemPermutation extends AbstractIntegerPermutationProblem
    implements ConstrainedProblem<PermutationSolution<Integer>> {

  private int age;
  private String gender;
  private Db db = new Db();
  private int numberOfConstraints = 0;

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
  }

  @Override
  public int getNumberOfConstraints() {
    return numberOfConstraints;
  }

  @Override
  public void evaluateConstraints(PermutationSolution<Integer> solution) {}

  @Override
  public void evaluate(PermutationSolution<Integer> solution) {
    //solution.ge
  }

  @Override
  public int getPermutationLength() {
    return getNumberOfVariables();
  }
}
