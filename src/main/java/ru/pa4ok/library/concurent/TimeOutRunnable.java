package ru.pa4ok.library.concurent;

import java.util.concurrent.TimeUnit;

public interface TimeOutRunnable extends Runnable
{
    public long getTimeout();

    public TimeUnit getTimeUnit();
}

