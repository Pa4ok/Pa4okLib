package ru.pa4ok.library.swing;

import javax.swing.*;
import java.awt.*;

public class FontUtil
{
    public static void changeAllFonts(Font font)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
    }
}
