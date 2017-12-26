package net.orhanbalci.fuzzymoea.problem;

public class ConstraintResult {

  private ConstraintResultCode resultCode;
  private float violationAmount = 0.0f;

  public ConstraintResult(ConstraintResultCode resultCode, float violationAmount) {
    this.resultCode = resultCode;
    this.violationAmount = violationAmount;
  }

  public ConstraintResultCode getResultCode() {
    return resultCode;
  }

  public float getViolationAmount() {
    return violationAmount;
  }
}
