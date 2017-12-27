
package net.orhanbalci.fuzzymoea.fuzzy;

import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.plot.*;
import net.sourceforge.jFuzzyLogic.rule.*;

public class FuzzyCalculator {
  private FIS fis;
  private FunctionBlock dietOptimizer;

  public FuzzyCalculator(String fileName) {
    fis = FIS.load(fileName);
    if (fis == null) {
      System.err.println("Can't load file: '" + fileName + "'");
    } else {
      dietOptimizer = fis.getFunctionBlock("dietOptimizer");
    }
  }

  public double calculateIdeal(int preference, int preparationTime, int rating) {

    fis.setVariable("preference", preference);
    fis.setVariable("preparation_time", preparationTime);
    fis.setVariable("rating", rating);

    // Evaluate
    fis.evaluate();

    // Show output variable's chart
    Variable tip = dietOptimizer.getVariable("ideal");
    //JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

    // Print ruleSet
    //System.out.println(fis);
    return tip.getLatestDefuzzifiedValue();
  }

  public void showCharts(int preference, int preparationTime, int rating) {
    fis.setVariable("preference", preference);
    fis.setVariable("preparation_time", preparationTime);
    fis.setVariable("rating", rating);

    // Evaluate
    fis.evaluate();

    // Show output variable's chart
    Variable tip = dietOptimizer.getVariable("ideal");
    JFuzzyChart.get().chart(dietOptimizer);
    JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

    // Print ruleSet
    //System.out.println(fis);

  }
}
