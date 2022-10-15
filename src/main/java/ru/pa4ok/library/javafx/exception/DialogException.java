package ru.pa4ok.library.javafx.exception;

public class DialogException extends Exception
{
    public DialogException(String message) {
        super(message);
    }

    public DialogException(String message, Throwable cause) {
        super(message, cause);
    }
}
