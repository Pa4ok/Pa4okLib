package ru.pa4ok.library.spring.hibernate.event;

public interface FixedEntityEvent<T>
{
    Class<T> getEntityClass();

    default boolean checkEntity(Object entity) {
        return entity != null && getEntityClass().isAssignableFrom(entity.getClass());
    }
}
