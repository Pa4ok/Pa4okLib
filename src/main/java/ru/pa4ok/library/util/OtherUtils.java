package ru.pa4ok.library.util;

public class OtherUtils
{
    public static final String DEPRECATED_ELEMENT = "|DEPRECATED|";

    public static void uncheckedSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    public static Double parseRusDouble(String s)
    {
        if(s == null || s.isEmpty()) {
            return null;
        }

        try {
            s = s.replaceAll(",", ".");
            s = s.trim();
            return Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean detectIdea()
    {
        try {
            Class.forName("com.intellij.rt.execution.application.AppMainV2");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
