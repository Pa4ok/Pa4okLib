package ru.pa4ok.library.swing;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

public class MessageWithLink extends JEditorPane
{
    public MessageWithLink(String htmlBody)
    {
        super("text/html", "<html><body style=\"" + getStyle() + "\">" + htmlBody + "</body></html>");
        addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
        setEditable(false);
        setBorder(null);
    }

    private static StringBuffer getStyle()
    {
        JLabel label = new JLabel();
        Font font = label.getFont();
        Color color = label.getBackground();

        return new StringBuffer("font-family:" + font.getFamily() + ";")
                .append("font-weight:").append(font.isBold() ? "bold" : "normal").append(";")
                .append("font-size:").append(font.getSize()).append("pt;")
                .append("background-color: rgb(").append(color.getRed()).append(",").append(color.getGreen()).append(",").append(color.getBlue()).append(");");
    }
}