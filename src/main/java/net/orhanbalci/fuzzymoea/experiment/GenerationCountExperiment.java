
package net.orhanbalci.fuzzymoea.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.orhanbalci.fuzzymoea.problem.DietProblemPermutation;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class GenerationCountExperiment {

  private String baseDirectory;
  private String gender;
  private int age;
  private int independentRuns;

  public GenerationCountExperiment(
      int independentRuns, String experimentBaseDirectory, int age, String gender) {
    baseDirectory = experimentBaseDirectory;
    this.gender = gender;
    this.age = age;
    this.independentRuns = independentRuns;
  }

  public void run() throws IOException {
    List<ExperimentProblem<PermutationSolution<Integer>>> problemList = new ArrayList<>();
    problemList.add(
        new ExperimentProblem<>(
            new DietProblemPermutation("DietProblem", age, gender, "experiment.fcl")));

    List<ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>>
        algorithmList = new ArrayList<>();
    algorithmList.add(
        new ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>(
            new NSGAIIBuilder<PermutationSolution<Integer>>(
                    problemList.get(0).getProblem(),
                    new PMXCrossover(0.9),
                    new PermutationSwapMutation(0.6))
                .setMaxEvaluations(2500)
                .setPopulationSize(100)
                .build(),
            "NSGAIIWithGenerationCount2500",
            problemList.get(0).getTag()));

    algorithmList.add(
        new ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>(
            new NSGAIIBuilder<PermutationSolution<Integer>>(
                    problemList.get(0).getProblem(),
                    new PMXCrossover(0.9),
                    new PermutationSwapMutation(0.6))
                .setMaxEvaluations(5000)
                .setPopulationSize(100)
                .build(),
            "NSGAIIWithGenerationCount5000",
            problemList.get(0).getTag()));

    algorithmList.add(
        new ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>(
            new NSGAIIBuilder<PermutationSolution<Integer>>(
                    problemList.get(0).getProblem(),
                    new PMXCrossover(0.9),
                    new PermutationSwapMutation(0.6))
                .setMaxEvaluations(10000)
                .setPopulationSize(100)
                .build(),
            "NSGAIIWithGenerationCount10000",
            problemList.get(0).getTag()));

    algorithmList.add(
        new ExperimentAlgorithm<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>(
            new NSGAIIBuilder<PermutationSolution<Integer>>(
                    problemList.get(0).getProblem(),
                    new PMXCrossover(0.9),
                    new PermutationSwapMutation(0.6))
                .setMaxEvaluations(15000)
                .setPopulationSize(100)
                .build(),
            "NSGAIIWithGenerationCount15000",
            problemList.get(0).getTag()));

    Experiment<PermutationSolution<Integer>, List<PermutationSolution<Integer>>> experiment =
        new ExperimentBuilder<PermutationSolution<Integer>, List<PermutationSolution<Integer>>>(
                "GenerationCountExperiment")
            .setAlgorithmList(algorithmList)
            .setProblemList(problemList)
            .setExperimentBaseDirectory(baseDirectory)
            .setReferenceFrontDirectory(baseDirectory + "/" + "ReferenceFront")
            .setOutputParetoFrontFileName("FUN")
            .setOutputParetoSetFileName("VAR")
            .setIndicatorList(Arrays.asList(new PISAHypervolume<PermutationSolution<Integer>>()))
            .setIndependentRuns(independentRuns)
            .build();

    new ExecuteAlgorithms<>(experiment).run();
    new GenerateReferenceParetoFront(experiment).run();
    new ComputeQualityIndicators<>(experiment).run();
    new GenerateLatexTablesWithStatistics(experiment).run();
    new GenerateWilcoxonTestTablesWithR<>(experiment).run();
    new GenerateFriedmanTestTables<>(experiment).run();
    new GenerateBoxplotsWithR<>(experiment).setRows(3).setColumns(3).run();
  }
}
