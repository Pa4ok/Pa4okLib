package ru.pa4ok.library.common;

import java.util.function.Predicate;

public class DataFilter
{
    public static final Predicate<Integer> POSITIVE_INT_FILTER = createBoundIntFilter(0, Integer.MAX_VALUE);
    public static final Predicate<Long> POSITIVE_LONG_FILTER = createBoundLongFilter(0L, Long.MAX_VALUE);
    public static final Predicate<Double> POSITIVE_DOUBLE_FILTER = createBoundDoubleFilter(0D, Double.MAX_VALUE);

    public static Predicate<String> createBoundStringFilter(int min, int max) {
        return s -> s.length() >= min && s.length() <= max;
    }

    public static Predicate<Integer> createBoundIntFilter(int min, int max) {
        return i -> i >= min && i <= max;
    }

    public static Predicate<Long> createBoundLongFilter(long min, long max) {
        return l -> l >= min && l <= max;
    }

    public static Predicate<Double> createBoundDoubleFilter(double min, double max) {
        return d -> !(d < min) && !(d > max);
    }
}
