package ru.pa4ok.library.concurent;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public abstract class TimeOutCallable<T> extends TimeOutTask implements Callable<T>
{
    public TimeOutCallable(long timeout, TimeUnit timeUnit) {
        super(timeout, timeUnit);
    }

    public TimeOutCallable(long timeout) {
        super(timeout);
    }
}
