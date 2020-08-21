package ru.pa4ok.library.swing;

import javax.swing.JOptionPane;
import java.awt.Component;

public class DialogUtil
{
    public static void showError(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, " Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(String text)
    {
        showError(null, text);
    }

    public static void showWarn(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, " Предупреждение", JOptionPane.WARNING_MESSAGE);
    }

    public static void showWarn(String text)
    {
        showWarn(null, text);
    }

    public static void showInfo(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, " Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfo(String text)
    {
        showInfo(null, text);
    }

    public static boolean showConfirm(Component parentComponent, String text)
    {
        int result = JOptionPane.showOptionDialog(parentComponent, text, " Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Подтверждение", "Отмена"}, "Подтверждение");

        if (result == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    public static boolean showConfirm(String text) {
        return showConfirm(null, text);
    }
}
