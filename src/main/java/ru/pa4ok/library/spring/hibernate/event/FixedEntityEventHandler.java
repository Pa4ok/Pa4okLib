package ru.pa4ok.library.spring.hibernate.event;

import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;

public interface FixedEntityEventHandler<T> extends FixedEntityEvent<T>, PostInsertEventListener, PostUpdateEventListener, PreDeleteEventListener
{
    void onPostInsert(PostInsertEvent event, T entity);

    void onPostUpdate(PostUpdateEvent event, T entity);

    boolean onPreDelete(PreDeleteEvent event, T entity);

    @Override
    @SuppressWarnings("unchecked")
    default void onPostInsert(PostInsertEvent event)
    {
        if(checkEntity(event.getEntity())) {
            onPostInsert(event, (T) event.getEntity());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    default void onPostUpdate(PostUpdateEvent event)
    {
        if(checkEntity(event.getEntity())) {
            onPostUpdate(event, (T) event.getEntity());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    default boolean onPreDelete(PreDeleteEvent event)
    {
        if(checkEntity(event.getEntity())) {
            return onPreDelete(event, (T) event.getEntity());
        }
        return false;
    }

    @Override
    default boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }
}
