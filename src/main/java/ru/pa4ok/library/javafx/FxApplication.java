package ru.pa4ok.library.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class FxApplication extends Application
{
    protected Stage stage;
    private String title;
    private boolean resizable;
    private Parent root;

    protected void initUi(String title, boolean resizable, Parent root)
    {
        this.title = title;
        this.resizable = resizable;
        this.root = root;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        stage.setTitle(title);
        stage.setResizable(resizable);
        changeScene(root);
        stage.show();
    }

    public void stopApp()
    {
        onAppStop();
        Platform.exit();
    }

    protected abstract void onAppStop();

    public void changeScene(Parent root)
    {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public Stage getStage() {
        return stage;
    }
}
