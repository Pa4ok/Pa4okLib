package ru.pa4ok.library.ui.form;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

public abstract class BaseForm extends JFrame
{
    private static String baseApplicationTitle = "Base application title";
    private static Image baseApplicationIcon = null;

    public BaseForm()
    {
        setTitle(getBaseApplicationTitle());
        if(baseApplicationIcon != null) {
            setIconImage(baseApplicationIcon);
        }
        setMinimumSize(new Dimension(getFormWidth(), getFormHeight()));
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

    public static String getBaseApplicationTitle() {
        return baseApplicationTitle;
    }

    public static void setBaseApplicationTitle(String baseApplicationTitle) {
        BaseForm.baseApplicationTitle = baseApplicationTitle;
    }

    public static Image getBaseApplicationIcon() {
        return baseApplicationIcon;
    }

    public static void setBaseApplicationIcon(Image baseApplicationIcon) {
        BaseForm.baseApplicationIcon = baseApplicationIcon;
    }
}
