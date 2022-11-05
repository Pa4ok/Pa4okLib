package ru.pa4ok.library.spring.hibernate.repository;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("unchecked")
public class GenericRepository<P> implements ApplicationContextAware
{
    private Repositories repositories = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        repositories = new Repositories(applicationContext);
    }


    public <T> JpaRepository<T,P> getRepository(Class<T> cls) {
        return (JpaRepository<T,P>) repositories.getRepositoryFor(cls)
                .orElseThrow(() -> new RuntimeException("No repository for class: " + cls.getName())
                );
    }

    public <T> Optional<T> findById(Class<T> cls, P id) {
        return getRepository(cls).findById(id);
    }

    public <T> T findByIdOrNull(Class<T> cls, P id) {
        return getRepository(cls).findById(id).orElse(null);
    }

    public <T> T findByIdOrAdd(Class<T> cls, P id, T entity) {
        return getRepository(cls).findById(id).orElseGet(() -> save(cls, entity));
    }

    public <T> T findByIdOrAdd(P id, T entity) {
        Class<T> cls = (Class<T>) entity.getClass();
        return getRepository(cls).findById(id).orElseGet(() -> save(cls, entity));
    }

    public <T> List<T> findAll(Class<T> cls) {
        return getRepository(cls).findAll();
    }

    public <T> T save(Class<T> cls, T entity) {
        return getRepository(cls).save(entity);
    }

    public <T> T save(T entity) {
        return getRepository((Class<T>)entity.getClass()).save(entity);
    }

    public <T> List<T> saveAll(Class<T> cls, List<T> entities) {
        return getRepository(cls).saveAll(entities);
    }

    public <T> void deleteById(Class<T> cls, P id) {
        getRepository(cls).deleteById(id);
    }

    public <T> void delete(T entity) {
        getRepository((Class<T>)entity.getClass()).delete(entity);
    }
}