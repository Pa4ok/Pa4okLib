package ru.pa4ok.library.concurent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExceptionCheckExecutor extends ThreadPoolExecutor
{
    private final String name;

    public ExceptionCheckExecutor(String name, int minCores, int maxCores, BlockingQueue<Runnable> queue)
    {
        super(minCores, maxCores, 1, TimeUnit.MINUTES, queue);
        this.name = name;
    }

    public ExceptionCheckExecutor(String name, int minCores, int maxCores)
    {
        this(name, minCores, maxCores, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable throwable)
    {
        super.afterExecute(r, throwable);

        if (throwable == null && r instanceof Future<?>) {
            try {
                @SuppressWarnings("unused")
                Object result = ((Future<?>) r).get();
            } catch (CancellationException e0) {
                throwable = e0;
            } catch (ExecutionException e1) {
                throwable = e1.getCause();
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
        }

        if(throwable != null)
        {
            System.out.println("Exception in ThreadPoolExecutor " + this.name + ":");
            throwable.printStackTrace();
        }
    }

    public String getName()
    {
        return this.name;
    }
}

