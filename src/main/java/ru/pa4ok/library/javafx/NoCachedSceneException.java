package ru.pa4ok.library.javafx;

import javafx.scene.Parent;

public class NoCachedSceneException extends RuntimeException
{
    public NoCachedSceneException(Class<? extends Parent> cls) {
        super("Application can't find scene with cls '" + cls.getName() + "' in cache");
    }
}

