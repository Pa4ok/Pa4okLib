package ru.pa4ok.library.spring.controller;

import com.google.gson.Gson;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.pa4ok.library.util.GsonUtil;

import javax.servlet.ServletContext;
import java.io.IOException;

public class AbstractController
{
    protected final Gson gson = GsonUtil.GSON;
    protected final Gson gsonPretty = GsonUtil.GSON_PRETTY;

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

    protected ResponseEntity<String> error() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    protected MediaType getMediaType(ServletContext context, Resource resource)
    {
        String contentType = null;
        try {
            contentType = context.getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return MediaType.parseMediaType(contentType);
    }
}
