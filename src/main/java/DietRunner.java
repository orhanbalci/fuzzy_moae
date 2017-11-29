import java.util.List;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;

public class DietRunner extends AbstractAlgorithmRunner {
  public static void run() {
    Problem<BinarySolution> problem;
    Algorithm<List<BinarySolution>> algorithm;
    CrossoverOperator<BinarySolution> crossover;
    MutationOperator<BinarySolution> mutation;
    SelectionOperator<List<BinarySolution>, BinarySolution> selection;

    String problemName = "net.orhanbalci.fuzzymoea.problem.DietProblem";
    problem = ProblemUtils.<BinarySolution>loadProblem(problemName);
    double crossoverProbability = 0.9;
    // double crossoverDistributionIndex = 20.0;
    crossover = new SinglePointCrossover(crossoverProbability);

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    // double mutatationDistributionIndex = 20.0;
    mutation = new BitFlipMutation(mutationProbability);

    selection = new BinaryTournamentSelection<BinarySolution>();

    algorithm =
        new NSGAIIBuilder<BinarySolution>(problem, crossover, mutation)
            .setSelectionOperator(selection)
            .setMaxEvaluations(25000)
            .setPopulationSize(100)
            .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
    List<BinarySolution> population = algorithm.getResult();
    long computingTime = algorithmRunner.getComputingTime();
    JMetalLogger.logger.info("Total execution time : " + computingTime + "ms");

    printFinalSolutionSet(population);
  }
}
