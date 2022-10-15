package ru.pa4ok.library.functional;

import java.util.function.Supplier;

@FunctionalInterface
public interface CheckedSupplier<T> extends Supplier<T>, Checked
{
    @Override
    default T get() {
        try {
            return checkedGet();
        } catch (Throwable t) {
            this.onThrowable(t);
            return null;
        }
    }

    T checkedGet() throws Throwable;
}
