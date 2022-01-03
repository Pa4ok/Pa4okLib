package ru.pa4ok.library.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil
{
    public static String getMD5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(number.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDoubleMD5(String input)
    {
        return getMD5(getMD5(input));
    }

    public static String getEncryptString(final String s)
    {
        final char[] mas = s.toCharArray();

        for (int i = 0; i < mas.length; ++i)
        {
            if (mas[i] >= 'a' && mas[i] <= 'z') {
                int k = mas[i];
                k = 122 - (26 - (123 - k));
                mas[i] = (char)k;
            } else if (mas[i] >= 'A' && mas[i] <= 'Z') {
                int k = mas[i];
                k = 90 - (26 - (91 - k));
                mas[i] = (char)k;
            } else if (mas[i] >= '0' && mas[i] <= '9') {
                int k = mas[i];
                k = 57 - (10 - (58 - k));
                mas[i] = (char)k;
            }
        }

        for (int i = 0; i < mas.length / 2; ++i) {
            final char ch = mas[i];
            mas[i] = mas[mas.length - 1 - i];
            mas[mas.length - 1 - i] = ch;
        }

        return new String(mas);
    }

    public static String encryptPassword(String password)
    {
        return getEncryptString(getMD5(getMD5(password)));
    }
}