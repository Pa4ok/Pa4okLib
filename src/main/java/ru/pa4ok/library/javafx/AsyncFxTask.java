package ru.pa4ok.library.javafx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.ImageCursor;
import lombok.Getter;
import org.apache.http.HttpException;
import org.apache.http.conn.HttpHostConnectException;
import ru.pa4ok.library.concurent.ConcurrentUtil;
import ru.pa4ok.library.functional.ThrowAction;
import ru.pa4ok.library.javafx.exception.DialogException;


import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * класс утилита
 */
@Getter
public class AsyncFxTask<T> extends Task<T>
{
    private static final ExecutorService SINGLE_EXECUTOR = Executors.newSingleThreadExecutor(ConcurrentUtil.FIXED_THREAD_FACTORY);
    private static final ExecutorService MULTIPLE_EXECUTOR = Executors.newFixedThreadPool(4, ConcurrentUtil.FIXED_THREAD_FACTORY);
    private static final Lock SINGLE_EXECUTOR_LOCK = new ReentrantLock();

    private final Callable<T> callable;
    private final Consumer<T> resultConsumer;
    private final boolean single;
    private final boolean cursorWait;
    private final boolean rejectIfBusy;

    private volatile ThrowAction onStart;
    private volatile ThrowAction onFinish;

    private final AtomicBoolean started = new AtomicBoolean();
    private volatile T result;

    public AsyncFxTask(Callable<T> callable, Consumer<T> resultConsumer, boolean single, boolean rejectIfBusy, boolean cursorWait) {
        this.callable = callable;
        this.resultConsumer = resultConsumer;
        this.single = single;
        this.rejectIfBusy = rejectIfBusy;
        this.cursorWait = cursorWait;
    }

    public static <T> AsyncFxTask<T> create(Callable<T> callable, Consumer<T> resultConsumer) {
        return new AsyncFxTask<>(callable, resultConsumer, false, false, true);
    }

    public static AsyncFxTask<Object> createVoid(ThrowAction action) {
        return new AsyncFxTask<>(action.callable(), null, false, false, true);
    }

    public static <T> AsyncFxTask<T> createSingle(Callable<T> callable, Consumer<T> resultConsumer, boolean rejectIfBusy) {
        return new AsyncFxTask<>(callable, resultConsumer, true, rejectIfBusy, true);
    }

    public static <T> AsyncFxTask<T> createSingle(Callable<T> callable, Consumer<T> resultConsumer) {
        return new AsyncFxTask<>(callable, resultConsumer, true, true, true);
    }

    public static AsyncFxTask<Object> createSingleVoid(ThrowAction action, boolean rejectIfBusy) {
        return new AsyncFxTask<>(action.callable(), null, true, rejectIfBusy, true);
    }

    public static AsyncFxTask<Object> createSingleVoid(ThrowAction action) {
        return new AsyncFxTask<>(action.callable(), null, true, true, true);
    }

    public void start()
    {
        if(!this.started.getAndSet(true))
        {
            if(this.single) {
                if(rejectIfBusy) {
                    if(SINGLE_EXECUTOR_LOCK.tryLock()) {
                        try {
                            SINGLE_EXECUTOR.submit(this);
                        } finally {
                            SINGLE_EXECUTOR_LOCK.unlock();
                        }
                    }
                } else {
                    SINGLE_EXECUTOR_LOCK.lock();
                    try {
                        SINGLE_EXECUTOR.submit(this);
                    } finally {
                        SINGLE_EXECUTOR_LOCK.unlock();
                    }
                }
            } else {
                MULTIPLE_EXECUTOR.submit(this);
            }
        }
    }

    @Override
    protected void scheduled()
    {
        if(this.cursorWait) {
            FxUtils.changeCursorOnAllStages(ImageCursor.WAIT);
        }
    }

    @Override
    protected T call() throws Exception
    {
        if(this.onStart != null) {
            this.runSyncPlatformAction(this.onStart);
        }

        this.result = this.callable.call();
        if(this.onFinish != null) {
            this.runSyncPlatformAction(this.onFinish);
        }

        return this.result;
    }

    @Override
    protected void succeeded()
    {
        if(this.cursorWait) {
            FxUtils.changeCursorOnAllStages(ImageCursor.DEFAULT);
        }

        if(this.resultConsumer != null) {
            this.resultConsumer.accept(this.result);
        }
    }

    @Override
    protected void cancelled()
    {
        if(this.cursorWait) {
            FxUtils.changeCursorOnAllStages(ImageCursor.DEFAULT);
        }
    }

    @Override
    protected void failed()
    {
        if(this.cursorWait) {
            FxUtils.changeCursorOnAllStages(ImageCursor.DEFAULT);
        }

        Throwable error = getException();
        String message = "Ошибка выполнения задачи " + this.getClass();

        if(error != null)
        {
            Throwable cause = error.getCause();

            if(error instanceof DialogException) {
                if(cause != null) {
                    DialogUtil.showError(error.getMessage(), cause);
                } else {
                    DialogUtil.showError(error.getMessage());
                }
                return;
            }

            if(error instanceof HttpException) {
                if(cause != null && error.getCause() instanceof HttpHostConnectException) {
                    message = "Отстутствует подключение к серверу";
                } else if(error.getMessage().startsWith("403")) {
                    message = "Ошибка доступа (403)";
                } else {
                    message = "Ошибка получения данных с сервера: " + error.getMessage();
                }
            }

            DialogUtil.showError(message, error);
        }
        else
        {
            DialogUtil.showError(message);
        }
    }

    private void runSyncPlatformAction(ThrowAction action) throws Exception
    {
        CountDownLatch lock = new CountDownLatch(1);
        AtomicReference<Exception> errorRef = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                action.action();
            } catch (Exception e) {
                errorRef.set(e);
            } finally {
                lock.countDown();
            }
        });

        lock.await();

        Exception e = errorRef.get();
        if(e != null) {
            throw e;
        }
    }

    public AsyncFxTask<T> setOnStart(ThrowAction onStart) {
        this.onStart = onStart;
        return this;
    }

    public AsyncFxTask<T> setOnFinish(ThrowAction onFinish) {
        this.onFinish = onFinish;
        return this;
    }
}
