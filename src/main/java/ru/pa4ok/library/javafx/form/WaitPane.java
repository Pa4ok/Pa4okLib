package ru.pa4ok.library.javafx.form;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import ru.pa4ok.library.javafx.DialogUtil;
import ru.pa4ok.library.javafx.FxUtils;

public class WaitPane extends VBox
{
    @FXML
    private ProgressIndicator barIndicator;

    @FXML
    private ProgressBar bar;

    @FXML
    private Label barLabel;

    private final Task<Void> task;
    private volatile Boolean interrupt = false;

    public WaitPane(Task<Void> task, String startLabel)
    {
        this.task = task;
        FxUtils.loadFxmlAndController(this);

        barLabel.setText(startLabel);
        bar.progressProperty().bind(task.progressProperty());
    }

    public void updateBarLabel(String text)
    {
        Platform.runLater(() -> barLabel.setText(text));
    }

    public void start()
    {
        if(this.task != null) {
            Thread thread = new Thread(task);
            thread.setDaemon(true);

            this.getScene().getWindow().setOnCloseRequest(event -> {
                if(!isInterrupt() && DialogUtil.showConfirm("Вы точно хотите прервать импорт данных?")) {
                    interrupt();
                    FxUtils.closeElementStage(this);
                }
            });

            thread.start();
        }
    }

    public Task<Void> getTask() {
        return task;
    }

    public ProgressIndicator getBarIndicator() {
        return barIndicator;
    }

    public ProgressBar getBar() {
        return bar;
    }

    public Label getBarLabel() {
        return barLabel;
    }

    public boolean isInterrupt() {
        synchronized (this.interrupt) {
            return interrupt;
        }
    }

    public void interrupt() {
        synchronized (this.interrupt) {
            this.interrupt = true;
        }
    }
}
