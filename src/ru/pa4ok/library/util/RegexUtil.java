package ru.pa4ok.library.util;

import java.util.regex.Pattern;

public class RegexUtil
{
    private static final Pattern login = Pattern.compile("[A-Za-z0-9]+");
    private static final Pattern email = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    private static final Pattern personNames = Pattern.compile("[A-Za-z \\- А-Яа-я]{0,35}");

    public static boolean isLoginCorrect(String s)
    {
        return (login.matcher(s)).matches();
    }

    public static boolean isEmailCorrect(String s)
    {
        return (email.matcher(s)).matches();
    }

    public static boolean isFirstnameCorrect(String s)
    {
        return (personNames.matcher(s)).matches();
    }

    public static boolean isSurnameCorrect(String s)
    {
        return (personNames.matcher(s)).matches();
    }

    public static boolean isPatronymicCorrect(String s)
    {
        if(s == null || s.equals("")) {
            return true;
        }
        return (personNames.matcher(s)).matches();
    }
}
