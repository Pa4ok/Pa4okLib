package ru.pa4ok.library.util;

import javax.xml.crypto.Data;

public interface DataFilter<T>
{
    public boolean filter(T s);

    public static final DataFilter<String> allowFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            return true;
        }
    };

    public static final DataFilter<String> denyFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            return false;
        }
    };

    public static final DataFilter<String> intFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };

    public static final DataFilter<String> positiveIntFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                if(Integer.parseInt(s) < 0) {
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };

    public static final DataFilter<String> longFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                Long.parseLong(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };

    public static final DataFilter<String> positiveLongFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                if(Long.parseLong(s) < 0) {
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };

    public static final DataFilter<String> doubleFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                Double.parseDouble(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };

    public static final DataFilter<String> positiveDoubleFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                if(Double.parseDouble(s) < 0) {
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };

    public static final DataFilter<String> booleanFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    };

    public static DataFilter<String> createBoundStringFilter(int min, int max)
    {
        return new DataFilter<String>() {
            @Override
            public boolean filter(String s) {
                if(s.length() < min || s.length() > max) {
                    return false;
                }
                return true;
            }
        };
    }

    public static DataFilter<String> createBoundIntFilter(int min, int max)
    {
        return new DataFilter<String>() {
            @Override
            public boolean filter(String s) {
                try {
                    int i = Integer.parseInt(s);
                    if(i < min || i > max) {
                        return false;
                    }
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        };
    }

    public static DataFilter<String> createBoundLongFilter(long min, long max)
    {
        return new DataFilter<String>() {
            @Override
            public boolean filter(String s) {
                try {
                    long l = Long.parseLong(s);
                    if(l < min || l > max) {
                        return false;
                    }
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        };
    }

    public static DataFilter<String> createBoundDoubleFilter(double min, double max)
    {
        return new DataFilter<String>() {
            @Override
            public boolean filter(String s) {
                try {
                    double d = Double.parseDouble(s);
                    if(d < min || d > max) {
                        return false;
                    }
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        };
    }
}
