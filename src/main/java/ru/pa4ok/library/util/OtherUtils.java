package ru.pa4ok.library.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class OtherUtils
{
    public static final String DEPRECATED_ELEMENT = "|DEPRECATED|";

    public static final String OS_NAME = System.getProperty("os.name");
    public static final boolean WINDOWS = OS_NAME.startsWith("Windows");
    public static final boolean WINDOWS_7 = OS_NAME.startsWith("Windows 7");
    public static final boolean WINDOWS_8 = OS_NAME.startsWith("Windows 8");
    public static final int OS_BITS = getCorrectOSArch();

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

    public static List<String> execCmd(String cmd, boolean output) throws Exception
    {
        Process process = Runtime.getRuntime().exec(cmd);

        if(output)
        {
            List<String> list = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("Cp866")))) {
                String s;
                while((s = br.readLine()) != null) {
                    list.add(s);
                }
            }
            return list;
        }

        return null;
    }

    public static List<String> execCmd(String cmd) throws Exception
    {
        return execCmd(cmd, true);
    }

    private static int getCorrectOSArch()
    {
        if (WINDOWS) {
            return System.getenv("ProgramFiles(x86)") == null ? 32 : 64;
        }

        return System.getProperty("os.arch").contains("64") ? 64 : 32;
    }
}
