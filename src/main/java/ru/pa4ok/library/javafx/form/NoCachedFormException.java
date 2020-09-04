package ru.pa4ok.library.javafx.form;

import javafx.scene.Parent;

public class NoCachedFormException extends RuntimeException
{
    public NoCachedFormException(Class<? extends Parent> cls) {
        super("Application can't find scene with cls '" + cls.getName() + "' in cache");
    }
}

