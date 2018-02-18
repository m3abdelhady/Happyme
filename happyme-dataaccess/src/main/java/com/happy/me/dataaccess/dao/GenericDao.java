package com.happy.me.dataaccess.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

public interface GenericDao<T> extends Serializable{
	
	Query createQuery(String queryStr);

	T create(T t);

	void delete(Object id);

	T find(Object id);

	T update(T t);

	Object getSingleResultObject(Query query);
	
	/**
	 * This method should be implemented to handle save or update mechanism
	 * This should use of {@link #saveOrUpdate(Query, Object)}
	 * 
	 * @param entity
	 */
	T save(T entity);

	List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

	void delete(Long id);

	T find(Long id);

	List<T> findAll();

	List<T> findAllWithOrder(String fieldName);
	
}