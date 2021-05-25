package ru.pa4ok.library.javafx.concurent;

import ru.pa4ok.library.javafx.DialogUtil;
import ru.pa4ok.library.util.Action;

import java.io.IOException;

@FunctionalInterface
public interface Task
{
    void run() throws Exception;

    default void safeRun() {
        try {
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка получения данных с сервера");
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка: " + e.getMessage());
        }
    }

    default void onStartAction() {
        //ClientApplication.instance.getActiveStage().getScene().setCursor(ImageCursor.WAIT);
    }

    default void onFinishAction() {
        //ClientApplication.instance.getActiveStage().getScene().setCursor(ImageCursor.DEFAULT);
    }

    public static Task createCustomTask(Action taskAction, Action onStartAction, Action onFinishAction)
    {
        return new Task() {
            @Override
            public void run() throws Exception {
                taskAction.action();
            }

            @Override
            public void onStartAction() {
                onStartAction.action();
            }

            @Override
            public void onFinishAction() {
                onFinishAction.action();
            }
        };
    }
}
