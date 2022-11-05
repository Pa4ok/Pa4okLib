package ru.pa4ok.library.spring.hibernate.event;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

public interface FixedPostInsertEvent<T> extends FixedEntityEvent<T>, PostInsertEventListener
{
    void onPostInsert(PostInsertEvent event, T entity);

    @Override
    @SuppressWarnings("unchecked")
    default void onPostInsert(PostInsertEvent event)
    {
        if(checkEntity(event.getEntity())) {
            onPostInsert(event, (T) event.getEntity());
        }
    }

    @Override
    default boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }
}
