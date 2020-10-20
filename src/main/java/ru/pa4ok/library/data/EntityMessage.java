package ru.pa4ok.library.data;

import ru.pa4ok.library.util.GsonUtil;

/**
 * It uses for gson serialization of enums & etc...
 */
public class EntityMessage<T> extends GsonUtil.Jsonable
{
    private T entity;

    public EntityMessage(T entity) {
        this.entity = entity;
    }

    public EntityMessage() {}

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}

