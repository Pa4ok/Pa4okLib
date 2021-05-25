package ru.pa4ok.library.util;

@FunctionalInterface
public interface DataFilter<T>
{
    boolean filter(T s);

    DataFilter<String> allowFilter = s -> true;

    DataFilter<String> denyFilter = s -> false;

    DataFilter<String> intFilter = s -> {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    DataFilter<String> positiveIntFilter = s -> {
        try {
            if(Integer.parseInt(s) < 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    DataFilter<String> longFilter = s -> {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    DataFilter<String> positiveLongFilter = s -> {
        try {
            if(Long.parseLong(s) < 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    DataFilter<String> doubleFilter = s -> {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    DataFilter<String> positiveDoubleFilter = s -> {
        try {
            if(Double.parseDouble(s) < 0) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    DataFilter<String> booleanFilter = s -> {
        try {
            if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    };

    static DataFilter<String> createBoundStringFilter(int min, int max)
    {
        return s -> {
            if(s.length() < min || s.length() > max) {
                return false;
            }
            return true;
        };
    }

    static DataFilter<String> createBoundIntFilter(int min, int max)
    {
        return s -> {
            try {
                int i = Integer.parseInt(s);
                if(i < min || i > max) {
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    static DataFilter<String> createBoundLongFilter(long min, long max)
    {
        return s -> {
            try {
                long l = Long.parseLong(s);
                if(l < min || l > max) {
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    static DataFilter<String> createBoundDoubleFilter(double min, double max)
    {
        return s -> {
            try {
                double d = Double.parseDouble(s);
                if(d < min || d > max) {
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }
}
