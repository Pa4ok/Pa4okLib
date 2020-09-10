package ru.pa4ok.library.javafx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pa4ok.library.javafx.form.MenuForm;

import java.io.IOException;

public class FxUtils
{
    private static final Logger logger = LogManager.getLogger(FxUtils.class);

    public static void loadFxmlAndController(Parent parent)
    {
        long startMills = System.currentTimeMillis();

        FXMLLoader loader = new FXMLLoader(parent.getClass().getResource(parent.getClass().getSimpleName() + ".fxml"));
        loader.setRoot(parent);
        loader.setController(parent);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error while loading FXML & set up controller", e);
        }

        if(parent instanceof MenuForm) {
            ((MenuForm)parent).initMenu();
        }

        logger.debug("Form '" + parent.getClass().getSimpleName() + "' loaded by " + (System.currentTimeMillis() - startMills) + "ms");
    }

    public static void createPopupStage(Stage mainStage, String title, Parent root)
    {
        Stage stage = new Stage();
        stage.setTitle(title == null ? mainStage.getTitle() : title);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);

        stage.centerOnScreen();
        stage.show();
    }

    public static void createPopupStage(Stage mainStage, Parent root)
    {
        createPopupStage(mainStage, null, root);
    }

    public static void closeElementStage(Parent element)
    {
        ((Stage)element.getScene().getWindow()).close();
    }
}
