package ru.pa4ok.library.data.hibernate;

import java.util.List;

public interface AbstractService<T>
{
	public T save(T object);

	public void delete(T object);
	
	public void deleteById(long id);

	public List<T> getAll();

	public T getById(long id);
	
	public T getByIdOrAdd(long id, T object);
}
