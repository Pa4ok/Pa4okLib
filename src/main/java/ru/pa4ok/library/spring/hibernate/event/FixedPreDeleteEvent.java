package ru.pa4ok.library.spring.hibernate.event;

import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;

public interface FixedPreDeleteEvent<T> extends FixedEntityEvent<T>, PreDeleteEventListener
{
    boolean onPreDelete(PreDeleteEvent event, T entity);

    @Override
    @SuppressWarnings("unchecked")
    default boolean onPreDelete(PreDeleteEvent event)
    {
        if(checkEntity(event.getEntity())) {
            return onPreDelete(event, (T) event.getEntity());
        }
        return false;
    }
}
