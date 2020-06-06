package ru.pa4ok.library.util;

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

    public static final DataFilter<String> floatFilter = new DataFilter<String>() {
        @Override
        public boolean filter(String s) {
            try {
                Float.parseFloat(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };
}
