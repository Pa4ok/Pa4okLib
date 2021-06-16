package ru.pa4ok.library.javafx;

import javafx.application.Platform;
import javafx.scene.ImageCursor;
import org.apache.http.HttpException;
import org.apache.http.conn.HttpHostConnectException;
import ru.pa4ok.library.util.Action;
import ru.pa4ok.library.util.ActionWithException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class AsyncTask
{
    private static AtomicInteger taskCounter = new AtomicInteger(0);
    private static volatile Boolean singleTaskActive = false;

    private Action onStart = () -> FxUtils.changeCursorOnAllStages(ImageCursor.WAIT);
    private Action onFinish = () -> FxUtils.changeCursorOnAllStages(ImageCursor.DEFAULT);

    private Consumer<Exception> exceptionHandler = e ->
    {
        //TODO: some logging here
        e.printStackTrace();

        String message = e.getMessage();
        String errorToShow;

        if(e instanceof HttpException) {
            if(e.getCause() != null && e.getCause() instanceof HttpHostConnectException) {
                errorToShow = "Отстутствует подключение к серверу";
            } else if(message.toLowerCase().startsWith("403")) {
                errorToShow = "Ошибка доступа (403)";
            } else {
                errorToShow = "Ошибка получения данных с сервера: " + message;
            }
        } else {
            errorToShow = "Ошибка: " + message;
        }

        DialogUtil.showErrorLater(errorToShow);
    };

    private ActionWithException doInBackground;

    private boolean single;

    public AsyncTask(ActionWithException doInBackground) {
        this.doInBackground = doInBackground;
    }

    public static AsyncTask create(ActionWithException doInBackground)
    {
        return new AsyncTask(doInBackground);
    }

    public boolean runSingle()
    {
        synchronized (singleTaskActive)
        {
            if (singleTaskActive) {
                return false;
            }
            singleTaskActive = true;
        }

        this.single = true;
        this.start();
        return true;
    }

    public void run()
    {
        this.start();
    }

    private void start()
    {
        Thread thread = new Thread(() ->
        {
            if(onStart != null) {
                Platform.runLater(() -> onStart.action());
            }

            try {
                doInBackground.action();
            } catch (Exception e) {
                if(exceptionHandler != null) {
                    exceptionHandler.accept(e);
                } else {
                    throw new RuntimeException("Exception in async task", e);
                }
            } finally {
                finishSingle();
            }

            if(onFinish != null) {
                Platform.runLater(() -> onFinish.action());
            }
        });

        thread.setDaemon(true);
        thread.setName("TaskThread-" + taskCounter.incrementAndGet());
        thread.start();
    }

    private void finishSingle()
    {
        if(this.single) {
            synchronized (singleTaskActive) {
                singleTaskActive = false;
            }
        }
    }

    public Action getOnStart() {
        return onStart;
    }

    public AsyncTask setOnStart(Action onStart) {
        this.onStart = onStart;
        return this;
    }

    public Action getOnFinish() {
        return onFinish;
    }

    public AsyncTask setOnFinish(Action onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    public ActionWithException getDoInBackground() {
        return doInBackground;
    }

    public AsyncTask setDoInBackground(ActionWithException doInBackground) {
        this.doInBackground = doInBackground;
        return this;
    }

    public Consumer<Exception> getExceptionHandler() {
        return exceptionHandler;
    }

    public AsyncTask setExceptionHandler(Consumer<Exception> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public static boolean isSingleTaskActive() {
        return singleTaskActive;
    }
}
