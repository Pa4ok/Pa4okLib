package ru.pa4ok.library.spring.hibernate.event;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

public interface FixedPostUpdateEvent<T> extends FixedEntityEvent<T>, PostUpdateEventListener
{
    void onPostUpdate(PostUpdateEvent event, T entity);

    @Override
    @SuppressWarnings("unchecked")
    default void onPostUpdate(PostUpdateEvent event)
    {
        if(checkEntity(event.getEntity())) {
            onPostUpdate(event, (T) event.getEntity());
        }
    }

    @Override
    default boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }
}
