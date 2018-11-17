package cz.panjeskyne.service;

import java.util.List;

import javax.transaction.Transactional;

import cz.panjeskyne.form.PaginatedResult;
import cz.panjeskyne.model.AbstractEntity;

@Transactional
public interface BaseService<T extends AbstractEntity> {

	T find(Object id);
	
	T save(T entity);

	void delete(T entity);
	
	List<T> getAll();

	PaginatedResult<T> getAllPaginated(int page, int limit);

}