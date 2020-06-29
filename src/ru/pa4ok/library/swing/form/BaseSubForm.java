package ru.pa4ok.library.swing.form;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class BaseSubForm extends BaseForm
{
    protected final BaseForm mainForm;

    public BaseSubForm(BaseForm mainFormIn)
    {
        this.mainForm = mainFormIn;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeSubForm();
            }
        });
    }

    public void closeSubForm()
    {
        mainForm.setEnabled(true);
        mainForm.setFocusable(true);
        dispose();
    }

    public void openSubForm()
    {
        mainForm.setEnabled(false);
        mainForm.setFocusable(false);
        setVisible(true);
        setFocusable(true);
    }
}
