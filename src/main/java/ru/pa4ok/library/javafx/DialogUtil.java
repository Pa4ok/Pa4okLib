package ru.pa4ok.library.javafx;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DialogUtil
{
    private static void showAlert(Alert.AlertType type, String title, String headerText, String text)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private static void setOnWindowClosing(Dialog<?> dialog)
    {
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
    }

    public static void showError(String headerText, String text)
    {
        showAlert(Alert.AlertType.ERROR, "Ошибка", headerText, text);
    }

    public static void showError(String text)
    {
        showAlert(Alert.AlertType.ERROR, "Ошибка", null, text);
    }

    public static void showError(String headerText, Throwable error)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(headerText);
        setOnWindowClosing(alert);

        ButtonType copyButton = new ButtonType("Копировать лог");
        alert.getButtonTypes().add(copyButton);

        VBox dialogPaneContent = new VBox();
        Label label = new Label("Лог ошибки");
        TextArea textArea = new TextArea();

        String text;
        try(StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw)){
            error.printStackTrace(pw);
            text = sw.toString();
        } catch (Exception e) {
            error.printStackTrace();
            e.printStackTrace();
            text = "Ошибка получения лога: " + e.getMessage();
        }

        textArea.setText(text);
        dialogPaneContent.getChildren().addAll(label, textArea);
        alert.getDialogPane().setContent(dialogPaneContent);

        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.isPresent() && optional.get().getText().equals("Копировать лог")) {
            StringSelection stringSelection = new StringSelection((text != null && !text.isEmpty()) ? text : "Лог пустой :(");
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    public static void showError(Throwable error)
    {
        showError(error.getMessage(), error);
    }

    public static void showWarning(String headerText, String text)
    {
        showAlert(Alert.AlertType.WARNING, "Предупреждение", headerText, text);
    }

    public static void showWarning(String text)
    {
        showAlert(Alert.AlertType.WARNING, "Предупреждение", null, text);
    }

    public static void showInfo(String headerText, String text)
    {
        showAlert(Alert.AlertType.INFORMATION, "Информация", headerText, text);
    }

    public static void showInfo(String text)
    {
        showAlert(Alert.AlertType.INFORMATION, "Информация", null, text);
    }

    public static boolean showConfirm(String headerText, String text)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(headerText);
        alert.setContentText(text);
        setOnWindowClosing(alert);

        Optional<ButtonType> optional = alert.showAndWait();
        if(!optional.isPresent()) {
            return false;
        }

        ButtonType result = optional.get();
        return result == ButtonType.APPLY || result == ButtonType.OK || result == ButtonType.YES;
    }

    public static boolean showConfirm(String text) {
        return showConfirm(null, text);
    }

    public static String showButtonSelect(String headerText, String text, List<String> items)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выберите действие");
        alert.setHeaderText(headerText);
        alert.setContentText(text);
        setOnWindowClosing(alert);

        alert.getButtonTypes().clear();
        items.forEach(i -> {
            ButtonType button = new ButtonType(i);
            alert.getButtonTypes().add(button);
        });

        Optional<ButtonType> option = alert.showAndWait();
        return option.map(ButtonType::getText).orElse(null);
    }

    public static String showButtonSelect(String headerText, String text, String[] items)
    {
        return showButtonSelect(headerText, text, Arrays.asList(items));
    }

    public static <T> T showChoice(String headerText, String text, List<T> items, StringConverter<T> converter)
    {
        ChoiceDialog<T> choiceDialog = new ChoiceDialog<>(items.get(0), items);
        choiceDialog.setTitle("Выберите элемент");
        choiceDialog.setHeaderText(headerText);
        choiceDialog.setContentText(text);
        setOnWindowClosing(choiceDialog);

        if(converter != null) {
            for(Node n : choiceDialog.getDialogPane().getChildren()) {
                if(n instanceof Pane) {
                    for(Node n1 : ((Pane)n).getChildren()) {
                        if(n1 instanceof ComboBox) {
                            ((ComboBox)n1).setConverter(converter);
                        }
                    }
                }
            }
        }

        Optional<T> optional = choiceDialog.showAndWait();
        return optional.orElse(null);
    }

    public static <T> T showChoice(String headerText, String text, List<T> items)
    {
        return showChoice(headerText, text, items, null);
    }

    public static <T> T showChoice(String text, List<T> items)
    {
        return showChoice(null, text, items, null);
    }

    private static void showAlertLater(Alert.AlertType type, String title, String headerText, String text)
    {
        Platform.runLater(() -> showAlert(type, title, headerText, text));
    }

    public static void showErrorLater(String headerText, String text)
    {
        showAlertLater(Alert.AlertType.ERROR, "Ошибка", headerText, text);
    }

    public static void showErrorLater(String text)
    {
        showAlertLater(Alert.AlertType.ERROR, "Ошибка", null, text);
    }

    public static void showErrorLater(String headerText, Throwable error)
    {
        Platform.runLater(() -> showError(headerText, error));
    }

    public static void showErrorLater(Throwable error)
    {
        Platform.runLater(() -> showError(error));
    }

    public static void showWarningLater(String headerText, String text)
    {
        showAlertLater(Alert.AlertType.WARNING, "Предупреждение", headerText, text);
    }

    public static void showWarningLater(String text)
    {
        showAlertLater(Alert.AlertType.WARNING, "Предупреждение", null, text);
    }

    public static void showInfoLater(String headerText, String text)
    {
        showAlertLater(Alert.AlertType.INFORMATION, "Информация", headerText, text);
    }

    public static void showInfoLater(String text)
    {
        showAlertLater(Alert.AlertType.INFORMATION, "Информация", null, text);
    }
}

