package ru.pa4ok.library.concurent;

import java.util.concurrent.TimeUnit;

public abstract class TimeOutRunnable extends TimeOutTask implements Runnable
{
    public TimeOutRunnable(long timeout, TimeUnit timeUnit) {
        super(timeout, timeUnit);
    }

    public TimeOutRunnable(long timeout) {
        super(timeout);
    }
}

