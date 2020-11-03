package ru.pa4ok.library.javafx;

import javafx.scene.Parent;

public class NoCachedStageException extends RuntimeException
{
    public NoCachedStageException(Class<? extends Parent> cls) {
        super("Application can't find stage with cls '" + cls.getName() + "' in cache");
    }
}

