package ru.pa4ok.library.functional;

import java.util.function.Consumer;

@FunctionalInterface
public interface CheckedConsumer<T> extends Consumer<T>, Checked
{
    @Override
    default void accept(T object) {
        try {
            checkedAccept(object);
        } catch (Throwable t) {
            this.onThrowable(t);
        }
    }

    void checkedAccept(T t) throws Throwable;
}
