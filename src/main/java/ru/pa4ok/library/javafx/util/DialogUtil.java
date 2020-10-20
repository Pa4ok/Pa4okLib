package ru.pa4ok.library.javafx.util;

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

    public static void showError(String headerText, String text)
    {
        showAlert(Alert.AlertType.ERROR, "Ошибка", headerText, text);
    }

    public static void showError(String text)
    {
        showError(null, text);
    }

    public static void showWarning(String headerText, String text)
    {
        showAlert(Alert.AlertType.WARNING, "Предупреждение", headerText, text);
    }

    public static void showWarning(String text)
    {
        showWarning(null, text);
    }

    public static void showInfo(String headerText, String text)
    {
        showAlert(Alert.AlertType.INFORMATION, "Информация", headerText, text);
    }

    public static void showInfo(String text)
    {
        showInfo(null, text);
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
