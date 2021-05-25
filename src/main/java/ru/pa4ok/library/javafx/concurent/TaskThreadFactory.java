package ru.pa4ok.library.javafx.concurent;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskThreadFactory
{
    private static AtomicInteger counter = new AtomicInteger(0);
    private static volatile Boolean active = false;

    public static boolean setTask(Task task)
    {
        synchronized (active)
        {
            if(active) {
                return false;
            }
            active = true;
        }

        task.onStartAction();
        Thread thread =new Thread(() -> {
            task.safeRun();
            synchronized (active) {
                active = false;
                task.onFinishAction();
            }
        });
        thread.setDaemon(true);
        thread.setName("TaskThread-" + counter.incrementAndGet());
        thread.start();

        return true;
    }

    public static boolean checkTask() {
        return active;
    }

    public static void unBlockNewTask()
    {
        synchronized (active) {
            active = false;
        }
    }
}
