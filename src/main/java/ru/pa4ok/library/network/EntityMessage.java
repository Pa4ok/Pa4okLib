package ru.pa4ok.library.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * It uses for serialization of enums & etc...
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityMessage<T>
{
    private T entity;
}
