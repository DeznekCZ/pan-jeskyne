package cz.panjeskyne.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import cz.panjeskyne.dao.BaseDAO;
import cz.panjeskyne.form.PaginatedResult;
import cz.panjeskyne.model.db.AbstractEntity;

public abstract class AbstractBaseService<T extends AbstractEntity> implements BaseService<T> {
	
	@Autowired
	protected BaseDAO<T> dao;
	
	@Override
	public T find(Object id) {
		return dao.find(id);
	}
	
	/* (non-Javadoc)
	 * @see cz.panjeskyne.service.impl.BaseService#save(T)
	 */
	@Transactional
	public T save(T entity) {
		return dao.save(entity);
	}
	
	@Override
	public void delete(T entity) {
		dao.delete(entity);
	}
	
	@Override
	public List<T> getAll() {
		return dao.getAll();
	}
	
	@Override
	public PaginatedResult<T> getAllPaginated(int page, int limit) {
		return dao.getAllPaginated(page, limit);
	}
}
