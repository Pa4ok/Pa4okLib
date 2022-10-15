package ru.pa4ok.library.functional;

import java.util.function.Predicate;

@FunctionalInterface
public interface CheckedPredicate<T> extends Predicate<T>, Checked
{
    @Override
    default boolean test(T object) {
        try {
            return checkedTest(object);
        } catch (Throwable t) {
            this.onThrowable(t);
            return false;
        }
    }

    boolean checkedTest(T object) throws Throwable;
}
