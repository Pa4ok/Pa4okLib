package ru.pa4ok.library.javafx;

import com.sun.javafx.stage.StageHelper;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pa4ok.library.util.OtherUtils;

import java.util.ArrayList;
import java.util.List;

public class FxUtils
{
    private static final Logger logger = LogManager.getLogger(FxUtils.class);

    public static void loadFxmlAndController(Parent parent, String resourcePath, String resourceName)
    {
        long startMills = System.currentTimeMillis();

        FXMLLoader loader = new FXMLLoader(FxUtils.class.getClassLoader().getResource("fxml/" + resourcePath + resourceName + ".fxml"));
        loader.setRoot(parent);
        loader.setController(parent);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Error while loading FXML & set up controller: " + parent.getClass().getName() + " " + resourcePath + " " + resourceName, e);
        }

        logger.debug("Form '" + parent.getClass().getSimpleName() + "' loaded by " + (System.currentTimeMillis() - startMills) + "ms");
    }

    public static void loadFxmlAndController(Parent parent, String resourcePath)
    {
        loadFxmlAndController(parent, resourcePath, parent.getClass().getSimpleName());
    }

    public static void loadFxmlAndController(Parent parent)
    {
        loadFxmlAndController(parent, "");
    }

    public static Image loadImage(String resourcePath, boolean backgroundLoading)
    {
        return new Image("image/" + resourcePath, backgroundLoading);
    }

    public static Image loadImage(String resourcePath)
    {
        return loadImage(resourcePath, false);
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

        setStageSizesFromRootElement(stage);
        stage.centerOnScreen();
        stage.show();
    }

    public static void createPopupStageLater(Stage mainStage, String title, Parent root)
    {
        Platform.runLater(() -> createPopupStage(mainStage, title, root));
    }

    public static void createPopupStage(Stage mainStage, Parent root)
    {
        createPopupStage(mainStage, null, root);
    }

    public static void createPopupStageLater(Stage mainStage, Parent root)
    {
        Platform.runLater(() -> createPopupStage(mainStage, root));
    }

    public static void closeElementStage(Parent element)
    {
        ((Stage)element.getScene().getWindow()).close();
    }

    public static void closeElementStageLater(Parent element)
    {
        Platform.runLater(() -> closeElementStage(element));
    }

    public static void setStageSizesFromRootElement(Stage stage)
    {
        if(stage.getScene() == null) {
            logger.warn("Can't set stage sizes because scene is null");
            return;
        }

        Parent root = stage.getScene().getRoot();
        if(root == null) {
            logger.warn("Can't set stage sizes because scene's root is null");
            return;
        }

        double minWidth = root.minWidth(-1);
        double minHeight = root.minHeight(-1);
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);

        stage.setWidth(root.prefWidth(-1));
        stage.setHeight(root.prefHeight(-1));

        double maxWidth = root.maxWidth(-1);
        double maxHeight = root.maxHeight(-1);
        if(maxWidth <= minWidth && maxHeight <= minHeight) {
            stage.setResizable(false);
        } else {
            stage.setResizable(true);
            stage.setMaxWidth(maxWidth);
            stage.setMaxHeight(maxHeight);
        }
    }

    public static void checkStageForMaximized(Stage oldStage, Stage newStage)
    {
        if(oldStage.isMaximized()) {
            if(newStage.getMaxWidth() >= oldStage.getWidth() && newStage.getMaxHeight() >= oldStage.getHeight()) {
                newStage.setMaximized(true);
            }
        }
    }

    public static <T> void checkComboBox(ComboBox<T> comboBox)
    {
        List<T> listToRemove = new ArrayList<>();
        comboBox.getItems().stream()
                .filter(item -> String.valueOf(item).equals(OtherUtils.DEPRECATED_ELEMENT))
                .forEach(item -> listToRemove.add(item));
        comboBox.getItems().removeAll(listToRemove);
    }

    public static void changeCursorOnAllStages(Cursor cursor)
    {
        StageHelper.getStages().forEach(stage -> {
            Scene scene = stage.getScene();
            if(scene != null) {
                scene.setCursor(cursor);
            }
        });
    }

    public static void setZeroAnchorPaneConstrains(Node node)
    {
        AnchorPane.setTopAnchor(node, 0D);
        AnchorPane.setRightAnchor(node, 0D);
        AnchorPane.setBottomAnchor(node, 0D);
        AnchorPane.setLeftAnchor(node, 0D);
    }
}
