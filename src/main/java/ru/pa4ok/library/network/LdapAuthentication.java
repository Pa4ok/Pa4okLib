package ru.pa4ok.library.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * я ебал меня сосали
 * убил 3 дня на попытки разобраться с ебучей авторизацией по ldap
 * которая не работает толи из-за уебищности, то ли из-за самоподписанных сертификатов
 * высрал эту хуйню работающую через powershell
 * спасибо, что хоть так сработало...
 */
public class LdapAuthentication
{
    private static final boolean DEBUG_HUETA = true;

    public static boolean authenticate(String username, String password) throws Exception
    {
        try {
            String script = "(new-object directoryservices.directoryentry '','%username%','%password%').psbase.name -ne $null";
            script = script.replace("%username%", username);
            script = script.replace("%password%", password);

            String command = "powershell.exe " + script;
            if(DEBUG_HUETA) {
                System.out.println("Executing process: " + command);
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
