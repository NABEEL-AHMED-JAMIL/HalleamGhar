package hg.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import hg.model.BaseEntity;


/**
 * @author Shahzad Badar
 */

@Repository
public class BaseDao<T extends BaseEntity> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String NULL_ID_MESSAGE = "Id cannot be null.";

	@PersistenceContext
	protected EntityManager em;

	public EntityManager getEntityManager() {
		return em;
	}

	/**
	 * Find the entity of T with a given id.
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Class<T> persistanceClass, Serializable id) {
		if (id == null) {
			throw new IllegalArgumentException(NULL_ID_MESSAGE);
		}
		return em.find(persistanceClass, id);
	}

	/**
	 * Delete the given entity.
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot delete null entity.");
		}
		em.remove(entity);
	}

	/**
	 * Delete the entity of type T with the given Id.
	 * 
	 * @param id
	 */
	public void delete(Class<T> persistanceClass, Serializable id) {
		if (id == null) {
			throw new IllegalArgumentException(NULL_ID_MESSAGE);
		}
		em.remove(em.find(persistanceClass, id));
	}

	/**
	 * Returns all entities of type T.
	 * 
	 * @return
	 */
	public List<T> findAll(String entityName) {
		String sql = "select b from " + entityName + " b";
		return getResultList(sql);
	}

	/**
	 * Returns entity by property.
	 * 
	 * @return
	 */
	public List<T> findByProperty(String entityName, String property,
			Object value) {
		String sql = "select b from " + entityName + " b where b." + property
				+ "=" + value;
		return getResultList(sql);
	}

	public T findSingleEntityByProperty(String entityName, String property,
			Object value) {
		if (value instanceof String) {
			value = "'" + value + "'";
		}
		String sql = "select b from " + entityName + " b where b." + property
				+ "=" + value;
		return getSingleResult(sql);
	}

	/**
	 * Add a new entity.
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable persist(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot persist null entity.");
		}
		em.persist(entity);
		return entity.getId();
	}

	/**
	 * Result list for a JPQL query.
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getResultList(String query) {
		return em.createQuery(query).getResultList();
	}

	/**
	 * A single result, like count, max or a single row/column combo etc., for a
	 * native SQL query.
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getSingleResult(String sqlQuery) {
		return (T) em.createQuery(sqlQuery).getSingleResult();
	}

	/***************************************************************************************************
	 * 
	 * @param namedQuery
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getSingleNamedQueryResult(String namedQuery) {
		return (T) em.createNamedQuery(namedQuery).getSingleResult();
	}

	/****************************************************************************************************
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @return
	 */
	public T getSingleNamedQueryResult(String namedQuery, Object[] parameters) {
		return getSingleNamedQueryResult(namedQuery, parameters, null); // (T)em.createNamedQuery(namedQuery).se.getSingleResult();
	}

	/*****************************************************************************************************
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @return
	 */
	public List<T> getNamedQueryResultList(String namedQuery,
			Object[] parameters) {
		return getNamedQueryResultList(namedQuery, parameters, null); // (T)em.createNamedQuery(namedQuery).se.getSingleResult();
	}

	/****************************************************************************************************
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getSingleNamedQueryResult(String namedQuery, Object[] parameters,
			Object[] parametersName) {
		return (T) getPreparedNamedQuery(namedQuery, parameters, parametersName)
				.getSingleResult(); // (T)em.createNamedQuery(namedQuery).se.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	public Object getSingleAggregateNamedQueryResult(String namedQuery,
			Object[] parameters) {
		return getPreparedNamedQuery(namedQuery, parameters, null)
				.getSingleResult(); // (T)em.createNamedQuery(namedQuery).se.getSingleResult();

	}

	/*****************************************************************************************************
	 * 
	 * @param namedQuery
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getNamedQueryResultList(String namedQuery,
			Object[] parameters, Object[] parametersName) {
		return getPreparedNamedQuery(namedQuery, parameters, parametersName)
				.getResultList(); // (T)em.createNamedQuery(namedQuery).se.getSingleResult();
	}

	/*****************************************************************************************************
	 * 
	 * @param namedQueryName
	 * @param parameters
	 * @return
	 */
	private Query getPreparedNamedQuery(String namedQueryName,
			Object[] parameters, Object[] parametersName) {

		if (parametersName != null && parameters != null
				&& parametersName.length != parameters.length) {
			throw new RuntimeException(
					"length of parameters and parameters name array are not equals. parameters = "
							+ parameters.length + " -- parametersName = "
							+ parametersName.length);
		}

		Query namedQuery = em.createNamedQuery(namedQueryName);

		if (parameters != null && parameters.length > 0) {
			for (int i = 1; i <= parameters.length; i++) {
				if (parametersName == null) {
					namedQuery.setParameter(i, parameters[i - 1]);
				} else {
					namedQuery.setParameter(
							String.valueOf(parametersName[i - 1]),
							parameters[i - 1]);
				}
			}
		}

		return namedQuery;
	}

	/**
	 * Counts all the rows in the table for entity type T.
	 * 
	 * @return count of all the entities of type T
	 */
	public Long countAll(String entityName) {
		String sql = "select count(o) from " + entityName + " o";
		return (Long) em.createQuery(sql).getSingleResult();
	}

	/**
	 * Refresh the state of the instance from the database, overwriting changes
	 * made to the entity, if any.
	 * 
	 * @param entity
	 */
	public void refresh(T entity) {
		em.refresh(entity);
	}

	/**
	 * Merge the state of the given entity into the current persistence context. <br>
	 * <br>
	 * Simply delegates to merge(T entity) method in this class.
	 * 
	 * @see #merge
	 * @param entity
	 * @return the updated entity instance
	 */
	public T update(T entity) {
		return this.merge(entity);
	}

	/**
	 * Merge the state of the given entity into the current persistence context.
	 * 
	 * @see #update
	 * @param entity
	 * @return The updated entity instance. The state of the passed entity is
	 *         not changed; that is, it doesn't become managed.
	 */
	public T merge(T entity) {
		return em.merge(entity);
	}

	/**
	 * Detach the state of the given entity from the current persistence
	 * context.
	 * 
	 * @see #update
	 * @param entity
	 * @return The detached entity instance.
	 */
	/*
	 * public void detach(T entity) { em.detach(entity); }
	 */

	public void flush() {
		em.flush();
	}

}