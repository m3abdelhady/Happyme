package com.happy.me.dataaccess.dao.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.util.CollectionUtils;

import com.happy.me.dataaccess.dao.DBDao;

public abstract class JPADao<T> implements DBDao<T> {

	private static final long serialVersionUID = -632256975476559039L;

	@PersistenceContext
	protected EntityManager em;

	private Class<T> type;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JPADao() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	
	@Override
	public T create(final T t) {
		this.em.persist(t);
		return t;
	}

	@Override
	public void delete(final Object id) {
		this.em.remove(this.em.getReference(type, id));
	}
	
	@Override
	public void delete(final Long id) {
		this.em.remove(this.em.getReference(type, id));
	}

	@Override
	public T find(final Object id) {
		return (T) this.em.find(type, id);
	}
	
	@Override
	public T find(final Long id) {
		return (T) this.em.find(type, id);
	}

	@Override
	public T update(final T t) {
		return this.em.merge(t);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getSingleResultObject(Query query) {
		List list = query.getResultList();
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	protected T saveOrUpdate(Query query, T entity) {
	    final int NO_RESULT = 0;
	    final int RESULT = 1;

	    //should return a list of ONE result, 
	    // since the query should be finding unique objects
	    List<T> results = query.getResultList();
	    switch (results.size()) {
	        case NO_RESULT:
	            em.persist(entity);
	            return entity;
	        case RESULT:
	            return em.merge(entity);
	        default:
	            throw new NonUniqueResultException("Unexpected query results, " +
	                    results.size());
	    }
	} 
	
	@Override
	public Query createQuery (String queryStr) {
		return em.createQuery(queryStr, type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
		Query namedQuery = em.createNamedQuery(queryName);
		
		if(queryParams != null) {
			for (String s : queryParams.keySet())
				namedQuery.setParameter(s, queryParams.get(s));
		}
		
		return namedQuery.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Query query = em.createQuery("from " + type.getSimpleName());
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllWithOrder(String fieldName) {
		Query query = em.createQuery("from " + type.getSimpleName() + " order by " + fieldName);
		return query.getResultList();
	}
	
	@Override
	public T save(T entity) {
		Long id = null;
		try {
			Method m = entity.getClass().getMethod("getId");
			id = (Long) m.invoke(entity);
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
		Query query = em.createQuery("select from " + type.getSimpleName() + " where id = " + id);
		return saveOrUpdate(query, entity);
	}

}
