import java.util.List;
import net.orhanbalci.fuzzymoea.operator.ScrambleMutation;
import net.orhanbalci.fuzzymoea.util.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;

public class DietPermutationRunner extends AbstractAlgorithmRunner {
  public static void run() {
    Problem<PermutationSolution<Integer>> problem;
    Algorithm<List<PermutationSolution<Integer>>> algorithm;
    CrossoverOperator<PermutationSolution<Integer>> crossover;
    MutationOperator<PermutationSolution<Integer>> mutation;
    SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selection;

    String problemName = "net.orhanbalci.fuzzymoea.problem.DietProblemPermutation";
    problem = ProblemUtils.<PermutationSolution<Integer>>loadProblem(problemName);
    double crossoverProbability = 0.9;
    // double crossoverDistributionIndex = 20.0;
    crossover = new PMXCrossover(crossoverProbability);

    double mutationProbability = 1.0 / problem.getNumberOfVariables();
    // double mutatationDistributionIndex = 20.0;
    //mutation = new PermutationSwapMutation(mutationProbability);
    mutation = new ScrambleMutation(mutationProbability);

    selection = new BinaryTournamentSelection<PermutationSolution<Integer>>();

    algorithm =
        new NSGAIIBuilder<PermutationSolution<Integer>>(problem, crossover, mutation)
            .setSelectionOperator(selection)
            .setMaxEvaluations(1000)
            .setPopulationSize(100)
            .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
    List<PermutationSolution<Integer>> population = algorithm.getResult();
    long computingTime = algorithmRunner.getComputingTime();
    JMetalLogger.logger.info("Total execution time : " + computingTime + "ms");
    JMetalLogger.logger.info("Number of solution in population : " + population.size());

    printFinalSolutionSet(population);

    SolutionPrinter sp = new SolutionPrinter("child", 5);
    sp.writeSolution("VAR_CONVERTED.tsv", population);
  }
}
