package ru.pa4ok.library.functional;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface ThrowAction
{
    void action() throws Exception;

    default Callable<Object> callable() {
        return () -> {
            action();
            return null;
        };
    }
}
