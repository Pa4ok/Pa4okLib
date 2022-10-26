package ru.pa4ok.library.spring.hibernate;

import org.springframework.transaction.annotation.Transactional;
import ru.pa4ok.library.spring.controller.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface DefaultService<T>
{
    DefaultRepository<T> getRepository();

    @Transactional(readOnly = true)
    default Optional<T> findById(long id) {
        return getRepository().findById(id);
    }

    @Transactional(readOnly = true)
    default List<T> findAll() {
        return getRepository().findAll();
    }

    default T add(T entity) {
        return getRepository().save(entity);
    }

    default List<T> addAll(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    default T edit(T entity) {
        return getRepository().save(entity);
    }

    default List<T> editAll(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    default void delete(T entity) {
        getRepository().delete(entity);
    }

    default T deleteById(long id) {
        T entity = findById(id).orElseThrow(NotFoundException::new);
        delete(entity);
        return entity;
    }

    default void deleteAll(Iterable<T> entities) {
        getRepository().deleteAll(entities);
    }

    default void deleteAll() {
        getRepository().deleteAll();
    }
}
