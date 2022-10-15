package ru.pa4ok.library.functional;

public interface Checked
{
    default void onThrowable(Throwable t) {
        t.printStackTrace();
    }
}
