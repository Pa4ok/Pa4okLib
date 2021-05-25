package ru.pa4ok.library.javafx;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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

    private static void showAlertLater(Alert.AlertType type, String title, String headerText, String text)
    {
        Platform.runLater(() -> showAlert(type, title, headerText, text));
    }

    public static void showError(String headerText, String text)
    {
        showAlert(Alert.AlertType.ERROR, "Ошибка", headerText, text);
    }

    public static void showErrorLater(String headerText, String text)
    {
        showAlertLater(Alert.AlertType.ERROR, "Ошибка", headerText, text);
    }

    public static void showError(String text)
    {
        showAlert(Alert.AlertType.ERROR, "Ошибка", null, text);
    }

    public static void showErrorLater(String text)
    {
        showAlertLater(Alert.AlertType.ERROR, "Ошибка", null, text);
    }

    public static void showWarning(String headerText, String text)
    {
        showAlert(Alert.AlertType.WARNING, "Предупреждение", headerText, text);
    }

    public static void showWarningLater(String headerText, String text)
    {
        showAlertLater(Alert.AlertType.WARNING, "Предупреждение", headerText, text);
    }

    public static void showWarning(String text)
    {
        showAlert(Alert.AlertType.WARNING, "Предупреждение", null, text);
    }

    public static void showWarningLater(String text)
    {
        showAlertLater(Alert.AlertType.WARNING, "Предупреждение", null, text);
    }

    public static void showInfo(String headerText, String text)
    {
        showAlert(Alert.AlertType.INFORMATION, "Информация", headerText, text);
    }

    public static void showInfoLater(String headerText, String text)
    {
        showAlertLater(Alert.AlertType.INFORMATION, "Информация", headerText, text);
    }

    public static void showInfo(String text)
    {
        showAlert(Alert.AlertType.INFORMATION, "Информация", null, text);
    }

    public static void showInfoLater(String text)
    {
        showAlertLater(Alert.AlertType.INFORMATION, "Информация", null, text);
    }

    public static boolean showConfirm(String headerText, String text)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(headerText);
        alert.setContentText(text);

        ButtonType result = alert.showAndWait().get();
        if(result == ButtonType.APPLY || result == ButtonType.OK || result == ButtonType.YES) {
            return true;
        }
        return false;
    }

    public static boolean showConfirm(String text) {
        return showConfirm(null, text);
    }

}
