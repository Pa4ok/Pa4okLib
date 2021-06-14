package ru.pa4ok.library.data;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.pa4ok.library.util.GsonUtil;

public class AbstractController
{
    protected final Gson gson = GsonUtil.gson;
    protected final Gson gsonPretty = GsonUtil.gsonPretty;

    protected ResponseEntity okJson(Object o)
    {
        return ResponseEntity.ok(gson.toJson(o));
    }

    protected ResponseEntity okNull()
    {
        return ResponseEntity.ok(null);
    }

    protected ResponseEntity notFound()
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    protected ResponseEntity deny() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
