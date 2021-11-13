package ru.pa4ok.library.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * я ебал меня сосали
 * убил 3 дня на попытки разобраться с ебучей авторизацией по ldap
 * которая не работает то ли из-за уебищности, то ли из-за самоподписанных сертификатов
 * высрал эту хуйню работающую через powershell
 * спасибо, что хоть так сработало...
 */
public class LdapAuthentication
{
    private static final boolean LOCAL_DEBUG = false;

    public static boolean authenticate(String username, String password)
    {
        String command = "powershell.exe ";
        try {
            String script = "(new-object directoryservices.directoryentry '','%username%','%password%').psbase.name -ne $null";
            script = script.replace("%username%", username);
            script = script.replace("%password%", password);

            command += script;
            if(LOCAL_DEBUG) {
                System.out.println("Executing process: " + command.replace(password, "***qwerty***"));
            }
            Process powerShellProcess = Runtime.getRuntime().exec(command);
            powerShellProcess.getOutputStream().close();

            String line;

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    try {
                        return Boolean.parseBoolean(line.toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()))) {
                System.out.println("Executed command: " + command);
                while ((line = reader.readLine()) != null) {
                    System.out.println("###LDAP_AUTH_ERROR### " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
