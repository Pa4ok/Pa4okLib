package ru.pa4ok.library.data.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface FixedJpaRepository<T> extends JpaRepository<T, Long>
{
    public default T getById(long id)
    {
        Optional<T> optional = this.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public default List<T> getAll()
    {
        return (List<T>)this.findAll();
    }

    public default T getByIdOrAdd(long id, T object)
    {
        T t = this.getById(id);
        if(t != null) {
            return t;
        } else {
            return this.save(object);
        }
    }
}
