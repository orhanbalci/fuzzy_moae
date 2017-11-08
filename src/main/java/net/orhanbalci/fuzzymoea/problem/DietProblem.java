package net.orhanbalci.fuzzymoea.problem;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;

public class DietProblem extends AbstractBinaryProblem
    implements ConstrainedProblem<BinarySolution> {

  public DietProblem() {
    setName("Diet Problem");
    setNumberOfConstraints(2);
    setNumberOfObjectives(2);
    setNumberOfVariables(100);
  }

  @Override
  public void evaluate(BinarySolution solution) {}

  @Override
  public int getBitsPerVariable(int index) {
    return 1;
  }

  @Override
  public void evaluateConstraints(BinarySolution solution) {}
}
