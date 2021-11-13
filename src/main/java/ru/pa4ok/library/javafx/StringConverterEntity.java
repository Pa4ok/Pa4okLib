package ru.pa4ok.library.javafx;

import javafx.util.StringConverter;

public interface StringConverterEntity<T>
{
    String getConvertString(T obj);

    default StringConverter<T> getConverter()
    {
        return new StringConverter<T>() {
            @Override
            public String toString(T obj) {
                return getConvertString(obj);
            }

            @Override
            public T fromString(String s) {
                return null;
            }
        };
    }
}
