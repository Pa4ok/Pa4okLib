package ru.pa4ok.library.common;

@FunctionalInterface
public interface ActionWithException
{
    void action() throws Exception;
}
