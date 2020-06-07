package ru.pa4ok.library.concurent;

import java.util.concurrent.TimeUnit;

public class TimeOutTask
{
    private long timeout;
    private TimeUnit timeUnit;

    public TimeOutTask(long timeout, TimeUnit timeUnit)
    {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public TimeOutTask(long timeout) {
        this(timeout, TimeUnit.MILLISECONDS);
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}