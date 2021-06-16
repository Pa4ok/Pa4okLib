package ru.pa4ok.library.spring;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.pa4ok.library.spring.exception.UserAccessDeniedException;
import ru.pa4ok.library.spring.exception.UserAuthTimeoutException;
import ru.pa4ok.library.spring.exception.UserBadAuthException;
import ru.pa4ok.library.util.GsonUtil;

public class AbstractController
{
    protected final Gson gson = GsonUtil.gson;
    protected final Gson gsonPretty = GsonUtil.gsonPretty;

    protected ResponseEntity<String> okJson(Object o)
    {
        return ResponseEntity.ok(gson.toJson(o));
    }

    protected ResponseEntity<String> okNull()
    {
        return ResponseEntity.ok(null);
    }

    protected ResponseEntity<String> notFound()
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    protected ResponseEntity<String> deny() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @ExceptionHandler(UserBadAuthException.class)
    public ResponseEntity<String> UserBadAuthExceptionHandler(UserBadAuthException e)
    {
        return deny();
    }

    @ExceptionHandler(UserAccessDeniedException.class)
    public ResponseEntity<String> UserAccessDeniedExceptionHandler(UserAccessDeniedException e)
    {
        return deny();
    }

    @ExceptionHandler(UserAuthTimeoutException.class)
    public ResponseEntity<String> UserAuthTimeoutExceptionHandler(UserAuthTimeoutException e)
    {
        return ResponseEntity.status(600).body(null);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> MissingRequestHeaderExceptionHandler(MissingRequestHeaderException e)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing user info header");
    }
}
