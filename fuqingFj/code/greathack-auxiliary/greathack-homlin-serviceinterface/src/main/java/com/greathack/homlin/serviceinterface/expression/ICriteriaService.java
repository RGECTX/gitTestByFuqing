package com.greathack.homlin.serviceinterface.expression;

import com.greathack.homlin.pojo.expression.Criteria;
import com.greathack.homlin.pojo.expression.CriteriaSearchCriteria;

import java.util.List;


public interface ICriteriaService extends IBaseService<Criteria> {

	
	
	/**
	 * @Title : 获取直系上级条件列表
	 * @Description: 获取直系上级条件列表
	 * @version
	 * @param criteriaId
	 * @return
	 * @throws
	 */
	List<Criteria> getParents(long criteriaId);

	/**
	 * @Title : 获取子孙条件列表
	 * @Description: 获取子孙条件列表
	 * @version
	 * @param criteriaId
	 * @return
	 * @throws
	 */
	List<Criteria> getDescendants(long criteriaId);

	/**
	 * @Title : 获取父条件
	 * @Description: 获取父条件
	 * @version
	 * @param criteriaId
	 * @return
	 * @throws
	 */
	Criteria getParent(long criteriaId);

	/**
	 * @Title : 获取子条件列表(按sortNum倒序排序)
	 * @Description: 获取子条件列表
	 * @version
	 * @param criteriaId
	 * @return
	 * @throws
	 */
	List<Criteria> getChildren(long expressionId, long criteriaId);

	
	/**
	 * @Title : 搜索
	 * @Description: 搜索
	 * @version
	 * @param criteriaSearchCriteria
	 * @return
	 * @throws
	 */
	List<Criteria> search(CriteriaSearchCriteria criteriaSearchCriteria);
	
	long getSearchResultCount(CriteriaSearchCriteria criteria);
	
	/**
	 *	修改条件排序
	 * @param criteriaIdList
	 */
	void sort(List<Long> criteriaIdList);
	
	/**
	 * 
	 * @Title: 是否叶子节点
	 * @param criteriaId
	 * @return
	*/
	boolean isLeaf(long criteriaId);
}
