/**
 * 
 */
package com.greathack.homlin.daointerface.expression;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.expression.Criteria;
import com.greathack.homlin.pojo.expression.CriteriaSearchCriteria;

import java.util.List;


/**
 * @author greathack
 * 
 */
public interface ICriteriaDAO extends BaseDAO<Criteria> {

	
	List<Criteria> search(CriteriaSearchCriteria criteriaSearchCriteria);
	
	long getSearchResultCount(CriteriaSearchCriteria criteria);

	/**
	 * @Title : getDescendants
	 * @Description: 根据ID 获取子孙条件列表
	 */
	List<Criteria> getDescendants(long criteriaId);

}
