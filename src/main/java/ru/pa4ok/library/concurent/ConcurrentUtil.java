package ru.pa4ok.library.concurent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ConcurrentUtil
{
    public static final ThreadFactory FIXED_THREAD_FACTORY = r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setDaemon(true);
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("[ERROR] Uncaught exception in thread '" + t.getName() + "'");
            e.printStackTrace();
        });
        return thread;
    };

    public static void uncheckedSleep(long ms)
    {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
