package ru.pa4ok.library.network.exception;

public class UserBadAuthException extends RuntimeException
{
    public final String login;
    public final String clientHash;

    public UserBadAuthException(String login, String clientHash) {
        this.login = login;
        this.clientHash = clientHash;
    }

    public UserBadAuthException() {
        this(null, null);
    }
}
