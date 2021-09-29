package ru.pa4ok.library.util;

public class StringUtil
{
    public static String getBigDoubleString(double number)
    {
        String s = "0";

        if (number <= 1000) {
            s = String.format("%.0f", number);
        } else if (number >= 10E2D && number < 10E5D) {
            s = String.format("%.2fK", number / 10E2D);
        } else if (number >= 10E5D && number < 10E8D) {
            s = String.format("%.2fM", number / 10E5D);
        } else if (number >= 10E8D && number < 10E11D) {
            s = String.format("%.2fG", number / 10E8D);
        } else if (number >= 10E11D && number < 10E14D) {
            s = String.format("%.2fT", number / 10E11D);
        } else if (number >= 10E14D && number < 10E17D) {
            s = String.format("%.2fP", number / 10E14D);
        } else if (number >= 10E17D && number < 10E20D) {
            s = String.format("%.2fE", number / 10E17D);
        } else if (number >= 10E20D && number < 10E23D) {
            s = String.format("%.2fZ", number / 10E20D);
        } else if (number >= 10E23D && number < 10E26D) {
            s = String.format("%.2fY", number / 10E23D);
        }

        return s;
    }
}
