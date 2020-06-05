package ru.pa4ok.library.ui.dialog;

import javax.swing.JOptionPane;
import java.awt.Component;

public class DialogUtil
{
    protected void showError(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, " Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    protected void showError(String text)
    {
        showError(null, text);
    }

    protected void showWarn(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, " Предупреждение", JOptionPane.WARNING_MESSAGE);
    }

    protected void showWarn(String text)
    {
        showWarn(null, text);
    }

    protected void showInfo(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, " Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showInfo(String text)
    {
        showInfo(null, text);
    }
}
