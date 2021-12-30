package ru.pa4ok.library.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericRepository implements ApplicationContextAware
{
    private Repositories repositories = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        repositories = new Repositories(applicationContext);
    }

    @SuppressWarnings("unchecked")
    public <T> JpaRepository<T, Long> getRepository(Class<T> cls) {
        return (JpaRepository<T, Long>) repositories.getRepositoryFor(cls).orElseThrow(() -> new RuntimeException("No repository for class: " + cls.getName()));
    }

    public <T> List<T> findAll(Class<T> cls) {
        return getRepository(cls).findAll();
    }

    public <T> T findById(Class<T> cls, Long id) {
        return getRepository(cls).findById(id).orElse(null);
    }

    public <T> T save(Class<T> cls, T entity) {
        return getRepository(cls).save(entity);
    }

    public <T> void delete(Class<T> cls, T entity) {
        getRepository(cls).delete(entity);
    }
}