package com.greathack.homlin.serviceinterface.am;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.AmUnit;
import com.greathack.homlin.pojo.AmUnitSearchCriteria;
import com.greathack.homlin.pojo.SearchResult;

import java.util.List;
import java.util.Map;

public interface IAmUnitService {
	
	Map<Long, AmUnit> getOrgs();
	
	SearchResult<AmUnit> search(AmUnitSearchCriteria criteria);
	
	AmUnit add(AmUnit org) ;
	
	void  delete(Long orgId) throws ServiceException;

	/**
	 * 停用组织机构
	 *
	 * @param orgId
	 * @return
	 * @throws ServiceException
	 * @author
	 * @date
	 */
	void disable(long orgId) throws ServiceException;

	/**
	 * 启用组织机构
	 *
	 * @param orgId
	 * @return
	 * @throws ServiceException
	 * @author
	 * @date
	 */
	void enable(long orgId) throws ServiceException;
	
	void update(AmUnit org) throws ServiceException;
	
	AmUnit findById(Long orgId) throws ServiceException;
	
	/**
	 * 获取直系父级组织机构列表
	 * @param orgId 当前组织机构ID
	 * @return 直系父级组织机构列表
	 */
	List<AmUnit> getParents(Long orgId);

	/**
	 * 获取下级组织机构列表
	 * @param orgId 当前组织机构ID
	 * @return 下级组织机构列表
	 */
	List<AmUnit> getChildren(long orgId);
}
