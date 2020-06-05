package ru.pa4ok.library.ui.form;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public abstract class BaseForm extends JFrame
{
    private static final String APPLICATION_BASE_TITLE = "Base application title";

    public BaseForm()
    {
        setResizable(false);
        setTitle(getFormTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(getFormWidth(), getFormHeight()));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getFormWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getFormHeight() / 2);
        pack();
    }

    public void changeForm(BaseForm form){
        this.dispose();
        form.setVisible(true);
    }

    public abstract int getFormWidth();

    public abstract int getFormHeight();

    public String getFormTitle() {
        return APPLICATION_BASE_TITLE;
    }
}
