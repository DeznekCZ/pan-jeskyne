package cz.panjeskyne.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.panjeskyne.form.PaginatedResult;
import cz.panjeskyne.model.db.AbstractEntity;


public class AbstractBaseDAO<T extends AbstractEntity> implements BaseDAO<T> {
	
	@PersistenceContext
	private EntityManager em;
	
	private Class<T> entityClass;
	
	public AbstractBaseDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	public T find(Object id) {
		return em.find(entityClass, id);
	}
	
	/* (non-Javadoc)
	 * @see cz.panjeskyne.dao.BaseDAO#save(T)
	 */
	public T save(T entity) {
		if(entity.getId() == null) {
			em.persist(entity);
			return entity;
		} else {
			return update(entity);
		}
	}
	
	@Override
	public T update(T entity) {
		return em.merge(entity);
	}
	
	@Override
	public void delete(T entity) {
		em.remove(entity);
	}
	
	@Override
	public List<T> getAll() {
		return em.createQuery("Select t from " + entityClass.getSimpleName() + " t", entityClass).getResultList();
	}
	
	@Override
	public PaginatedResult<T> getAllPaginated(int page, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> from = cq.from(entityClass);

		cq.select(from);
		int startPosition = (page - 1) * limit;
		TypedQuery<T> query = em.createQuery(cq).setFirstResult(startPosition).setMaxResults(limit);
		
		List<T> items = query.getResultList();
		PaginatedResult<T> result = new PaginatedResult<>();
		result.setItems(items);
		result.setLimit(limit);
		result.setPage(page);
		result.setAllCount(getCount());
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see cz.panjeskyne.dao.BaseDAO#getReference(java.lang.Object)
	 */
	public T getReference(Object id) {
		if(id == null) {
			return null;
		}
		return em.getReference(entityClass, id);
	}
	
	public long getCount() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> from = cq.from(entityClass);
		cq.select(cb.count(from));
		return em.createQuery(cq).getSingleResult();
	}
}
