package ru.pa4ok.example.ui;

import ru.pa4ok.library.ui.form.BaseForm;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddSlotForm extends BaseForm
{
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;

    public AddSlotForm()
    {
        setContentPane(mainPanel);
        setResizable(true);
    }

    @Override
    public int getFormWidth() {
        return 400;
    }

    @Override
    public int getFormHeight() {
        return 400;
    }
}
