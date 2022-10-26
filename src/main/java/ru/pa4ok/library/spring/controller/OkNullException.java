package ru.pa4ok.library.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class OkNullException extends RuntimeException
{
    public OkNullException() {
    }

    public OkNullException(String message) {
        super(message);
    }

    public OkNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public OkNullException(Throwable cause) {
        super(cause);
    }

    public OkNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
