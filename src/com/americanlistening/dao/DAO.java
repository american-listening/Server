package com.americanlistening.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.lang.reflect.ParameterizedType;

/**
 * Interface for a Data Access Object (DAO).
 * 
 * @author Ethan Vrhel
 * @since 1.0
 *
 * @param <T> The type of object.
 */
public interface DAO<T> {
	
	/**
	 * Gets data object with by its id.
	 * 
	 * @param id The id to use.
	 * @return The respective object.
	 */
	Optional<T> get(long id);
	
	/**
	 * Returns all data objects in this DAO.
	 * 
	 * @return All objects.
	 */
	List<T> getAll();
	
	/**
	 * Saves an an object to this DAO.
	 * 
	 * @param t The object to save.
	 */
	void save(T t);
	
	/**
	 * Updates an object using parameters.
	 * 
	 * @param t The object to update.
	 * @param params The parameters to update with.
	 */
	void update(T t, Map<String, String> params);
	
	/**
	 * Deletes an object.
	 * 
	 * @param t The object to delete.
	 */
	void delete(T t);
}
