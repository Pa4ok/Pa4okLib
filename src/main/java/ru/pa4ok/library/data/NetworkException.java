package ru.pa4ok.library.data;

import ru.pa4ok.library.data.message.JsonServerMessage;

public class NetworkException extends Exception
{
    public NetworkException() {
        super("Server error: No extra data");
    }

    public NetworkException(String error) {
        super("Server error: " + error == null ? "No extra data" : error);
    }

    public NetworkException(JsonServerMessage message) {
        super("Server error: " + message.getError() == null ? "No extra data" : message.getError());
    }
}
