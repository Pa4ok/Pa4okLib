package ru.pa4ok.library.network.exception;

public class UserAccessDeniedException extends RuntimeException
{
    public final long userId;

    public UserAccessDeniedException(long userId) {
        this.userId = userId;
    }

    public UserAccessDeniedException() {
        this(-1L);
    }
}
