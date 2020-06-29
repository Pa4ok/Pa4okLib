package ru.pa4ok.library.concurent;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public interface TimeOutCallable<T> extends Callable<T>
{
    public long getTimeout();

    public TimeUnit getTimeUnit();
}
