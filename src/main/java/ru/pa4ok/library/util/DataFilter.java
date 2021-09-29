package ru.pa4ok.library.util;

import java.util.function.Predicate;

public class DataFilter
{
    public static final Predicate<Integer> POSITIVE_INT_FILTER = createBoundIntFilter(0, Integer.MAX_VALUE);
    public static final Predicate<Long> POSITIVE_LONG_FILTER = createBoundLongFilter(0L, Long.MAX_VALUE);
    public static final Predicate<Double> POSITIVE_DOUBLE_FILTER = createBoundDoubleFilter(0D, Double.MAX_VALUE);

    static Predicate<String> createBoundStringFilter(int min, int max)
    {
        return s -> {
           if(s.length() < min || s.length() > max) {
               return false;
          }
           return true;
      };
    }

    public static Predicate<Integer> createBoundIntFilter(int min, int max)
    {
        return i -> {
                if(i < min || i > max) {
                    return false;
                }
                return true;
        };
    }

    public static Predicate<Long> createBoundLongFilter(long min, long max)
    {
        return l -> {
                if(l < min || l > max) {
                    return false;
                }
                return true;
        };
    }

    public static Predicate<Double> createBoundDoubleFilter(double min, double max)
    {
        return d -> {
                if(d < min || d > max) {
                    return false;
                }
                return true;
        };
    }
}
