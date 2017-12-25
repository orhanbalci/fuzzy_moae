package net.orhanbalci.fuzzymoea.util;

public class Range<T extends Comparable<? super T>> {
  public enum RangeCheck {
    INCLUDE_LOWER {
      public boolean lower(int compareResult) {
        return compareResult <= 0;
      }

      public boolean upper(int compareResult) {
        return compareResult > 0;
      }
    },
    INCLUDE_UPPER {
      public boolean lower(int compareResult) {
        return compareResult < 0;
      }

      public boolean upper(int compareResult) {
        return compareResult >= 0;
      }
    },
    INCLUDE_BOTH {
      public boolean lower(int compareResult) {
        return compareResult <= 0;
      }

      public boolean upper(int compareResult) {
        return compareResult >= 0;
      }
    },
    INCLUDE_NONE {
      public boolean lower(int compareResult) {
        return compareResult < 0;
      }

      public boolean upper(int compareResult) {
        return compareResult > 0;
      }
    };

    public abstract boolean lower(int compareResult);

    public abstract boolean upper(int compareResult);
  }

  public final RangeCheck rangeCheck;
  public final T lower;
  public final T upper;
  public final boolean isCircular;

  public Range(RangeCheck rangeCheck, T lower, T upper) {
    this.rangeCheck = rangeCheck;
    this.lower = lower;
    this.upper = upper;
    this.isCircular = this.upper.compareTo(this.lower) < 0;
  }

  public boolean inRange(T t) {
    if (this.isCircular) {
      return this.rangeCheck.lower(-this.upper.compareTo(t))
          || this.rangeCheck.upper(-this.lower.compareTo(t));
    } else {
      return this.rangeCheck.lower(this.lower.compareTo(t))
          && this.rangeCheck.upper(this.upper.compareTo(t));
    }
  }
}
