package ru.pa4ok.library.spring.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DefaultRepository<T> extends JpaRepository<T, Long>
{
    default T findByIdOrNull(long id) {
        return findById(id).orElse(null);
    }

    default T findByIdOrAdd(long id, T object) {
        return findById(id).orElseGet(() -> save(object));
    }
}
