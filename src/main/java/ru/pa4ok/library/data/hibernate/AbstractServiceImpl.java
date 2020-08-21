package ru.pa4ok.library.data.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractServiceImpl<R extends CrudRepository<T, Long>, T> implements AbstractService<T>
{
	@Autowired 
	protected R repository;
	
	@Override
	public T getById(long id) 
	{
		Optional<T> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	public T save(T object) 
	{
		return repository.save(object);
	}
	
	@Override
	public void delete(T object) 
	{
		repository.delete(object);
	}

	@Override
	public void deleteById(long id) 
	{
		repository.deleteById(id);
	}

	@Override
	public List<T> getAll() 
	{
		return (List<T>)repository.findAll();
	}
	
	@Override
	public T getByIdOrAdd(long id, T object) 
	{
		T t = this.getById(id);
		if(t != null) {
			return t;
		} else {
			return this.save(object);
		}
	}
}
