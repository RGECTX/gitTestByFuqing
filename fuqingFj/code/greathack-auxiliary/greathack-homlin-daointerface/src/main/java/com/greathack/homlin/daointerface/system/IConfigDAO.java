/**
 * 
 */
package com.greathack.homlin.daointerface.system;

import com.greathack.homlin.pojo.system.Config;

import java.util.List;

/**
 * @author greathack
 *
 */
public interface IConfigDAO extends BaseDAO<Config> {
	
	public List<Config> findAll();
	
}
