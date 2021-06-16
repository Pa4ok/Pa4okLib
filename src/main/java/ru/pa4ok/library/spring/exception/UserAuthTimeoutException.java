package ru.pa4ok.library.spring.exception;

public class UserAuthTimeoutException extends RuntimeException
{
    public final long userId;

    public UserAuthTimeoutException(long userId) {
        this.userId = userId;
    }

    public UserAuthTimeoutException() {
        this(-1L);
    }
}
