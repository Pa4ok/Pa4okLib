package ru.pa4ok.library.util;

@FunctionalInterface
public interface ActionWithException
{
    void action() throws Exception;
}
