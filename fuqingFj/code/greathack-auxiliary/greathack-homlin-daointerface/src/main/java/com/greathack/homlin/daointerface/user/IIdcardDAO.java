package com.greathack.homlin.daointerface.user;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.user.Idcard;
import com.greathack.homlin.pojo.user.IdcardSearchCriteria;

import java.util.List;


/**
 * @author lbb
 *
 */
public interface IIdcardDAO extends BaseDAO<Idcard> {
	/**
	 * 搜索用户身份证件
	 * 
	 * @param criteria
	 * @return
	 */
	List<Idcard> search(IdcardSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(IdcardSearchCriteria criteria);
	
	/**
	 * 根据userId来删除信息
	 * @param userOutId
	 */
	void delUserIdcard(long userId);

}
