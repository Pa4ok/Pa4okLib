package ru.pa4ok.library.spring.hibernate;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * T - main (server)
 * K - secondary (client)
 */
public interface ConvertService<T,K>
{
    DefaultService<T> getService();

    Function<T,K> getConvertFunction();

    @Transactional(readOnly = true)
    default Optional<K> findById(long id) {
        return getService().findById(id).map(getConvertFunction());
    }

    @Transactional(readOnly = true)
    default List<K> findAll() {
        return getService().findAll().stream()
                .map(getConvertFunction())
                .collect(Collectors.toList());
    }

    default T add(K entity) {
        throw new UnsupportedOperationException();
    }

    default List<T> addAll(Iterable<K> entities) {
        throw new UnsupportedOperationException();
    }

    default T edit(K entity) {
        throw new UnsupportedOperationException();
    }

    default List<T> editAll(Iterable<K> entities) {
        throw new UnsupportedOperationException();
    }

    default T deleteById(long id) {
        return getService().deleteById(id);
    }

    default void delete(K entity) {
        throw new UnsupportedOperationException();
    }

    default void deleteAll() {
        getService().deleteAll();
    }

    default void deleteAll(Iterable<K> entities) {
        throw new UnsupportedOperationException();
    }
}
