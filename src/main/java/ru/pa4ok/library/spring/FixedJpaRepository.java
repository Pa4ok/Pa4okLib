package ru.pa4ok.library.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FixedJpaRepository<T> extends JpaRepository<T, Long>
{
    default T findByIdOrNull(long id) {
        return findById(id).orElse(null);
    }

    default T findByIdOrAdd(long id, T object) {
        return findById(id).orElseGet(() -> this.save(object));
    }
}
