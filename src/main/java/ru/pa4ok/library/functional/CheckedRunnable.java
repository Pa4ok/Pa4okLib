package ru.pa4ok.library.functional;

@FunctionalInterface
public interface CheckedRunnable extends Runnable, Checked
{
    @Override
    default void run() {
        try {
            this.checkedRun();
        } catch (Throwable t) {
            this.onThrowable(t);
        }
    }

    void checkedRun() throws Throwable;
}
