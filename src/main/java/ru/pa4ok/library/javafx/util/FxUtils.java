package ru.pa4ok.library.javafx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Robot;
import java.io.IOException;

public class FxUtils
{
    public static void loadFxmlAndController(Parent parent)
    {
        FXMLLoader loader = new FXMLLoader(parent.getClass().getResource(parent.getClass().getSimpleName() + ".fxml"));
        loader.setRoot(parent);
        loader.setController(parent);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error while loading FXML & set up controller", e);
        }
    }

    public static Stage createPopupStage(Stage mainStage, String title, Parent root)
    {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);

        stage.centerOnScreen();
        stage.show();

        return stage;
    }

    public static void processKeyboardClick(int key)
    {
        try {
            Robot r = new Robot();
            r.keyPress(key);
            r.keyRelease(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
