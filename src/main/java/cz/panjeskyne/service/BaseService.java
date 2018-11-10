package cz.panjeskyne.service;

import java.util.List;

import javax.transaction.Transactional;

import cz.panjeskyne.form.Form;
import cz.panjeskyne.form.PaginatedResult;
import cz.panjeskyne.model.AbstractEntity;

@Transactional
public interface BaseService<T extends AbstractEntity, F extends Form> {

	T find(Object id);
	
	T save(T entity);

	void delete(T entity);
	
	List<T> getAll();

	PaginatedResult<T> getAllPaginated(int page, int limit);
	
	void fillModelFromForm(F form, T model);

	void fillFormFromModel(F form, T model);

}