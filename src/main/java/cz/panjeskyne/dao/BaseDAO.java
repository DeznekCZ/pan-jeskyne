package cz.panjeskyne.dao;

import java.util.List;

import cz.panjeskyne.form.PaginatedResult;
import cz.panjeskyne.model.AbstractEntity;

public interface BaseDAO<T extends AbstractEntity> {

	T find(Object id);
	
	T save(T entity);
	
	T update(T entity);
	
	void delete(T entity);
	
	List<T> getAll();

	T getReference(Object id);

	PaginatedResult<T> getAllPaginated(int page, int limit);

	
}