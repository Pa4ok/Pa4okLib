package ru.pa4ok.library.javafx.util;

import javafx.scene.control.Alert;

public class DialogUtil
{
    //TODO: make all dialogs (warnings, errors, accept, etc...)

    public static void showError(String headerText, String text)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(headerText);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void showError(String text)
    {
        showError(null, text);
    }

    /*public static boolean showConfirm(String text)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setContentText(text);
        alert.getResult()
    }*/
}
