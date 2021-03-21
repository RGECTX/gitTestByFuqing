/**
 * 
 */
package com.greathack.homlin.daointerface.system;

import java.util.List;

/**
 * @author greathack
 *
 */
public interface BaseDAO<T> {
	
	public void add(T instance);
	
	public void delete(Object id);
	
	public void update(T instance);
	
	public T findById(Object id);
	
	public List<T> findByExample(T instance);

}
