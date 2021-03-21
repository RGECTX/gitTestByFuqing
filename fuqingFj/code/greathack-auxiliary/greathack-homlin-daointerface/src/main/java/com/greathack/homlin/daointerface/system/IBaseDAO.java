/**
 * 
 */
package com.greathack.homlin.daointerface.system;

import java.util.List;

/**
 * @author zhanghb
 *
 */
public interface IBaseDAO<T, PK> {
	
	public void add(T instance);

	public void delete(PK id);
	
	public void update(T instance);
	
	public T findById(PK id);
	
	public List<T> findByExample(T instance);

	public List<T> findAll();

}
