package ru.pa4ok.library.concurent;

import java.util.concurrent.TimeUnit;

public interface TimeOutRunnable extends Runnable
{
    long getTimeout();

    TimeUnit getTimeUnit();
}

