package ru.pa4ok.library.spring.hibernate;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * T - main (server)
 * K - secondary (client)
 */
public interface ConvertService<T,K>
{
    DefaultService<T> getService();

    Function<T,K> convert();

    default Function<K,T> restoreOnAdd() {
        throw new UnsupportedOperationException("this function need to be overridden for use add(k) & addAll(k[])");
    }

    default Function<K,T> restoreOnEdit() {
        throw new UnsupportedOperationException("this function need to be overridden for use edit(k) & editAll(k[]) & delete(k) & deleteAll(k[])");
    }

    @Transactional(readOnly = true)
    default Optional<K> findById(long id) {
        return getService().findById(id).map(convert());
    }

    @Transactional(readOnly = true)
    default List<K> findAll() {
        return getService().findAll().stream()
                .map(convert())
                .collect(Collectors.toList());
    }

    default T add(K entity) {
        return getService().add(restoreOnAdd().apply(entity));
    }

    default List<T> addAll(Iterable<K> entities) {
        return getService().addAll(StreamSupport.stream(entities.spliterator(), false)
                .map(restoreOnAdd())
                .collect(Collectors.toList()));
    }

    default T edit(K entity) {
        return getService().edit(restoreOnEdit().apply(entity));
    }

    default List<T> editAll(Iterable<K> entities) {
        return getService().editAll(StreamSupport.stream(entities.spliterator(), false)
                .map(restoreOnEdit())
                .collect(Collectors.toList()));
    }

    default T deleteById(long id) {
        return getService().deleteById(id);
    }

    default void delete(K entity) {
        getService().delete(restoreOnEdit().apply(entity));
    }

    default void deleteAll() {
        getService().deleteAll();
    }

    default void deleteAll(Iterable<K> entities) {
        getService().deleteAll(StreamSupport.stream(entities.spliterator(), false)
                .map(restoreOnEdit())
                .collect(Collectors.toList()));
    }
}
