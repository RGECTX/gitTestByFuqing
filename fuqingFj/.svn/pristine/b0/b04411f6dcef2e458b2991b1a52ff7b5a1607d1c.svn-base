package com.greathack.homlin.daointerface.user;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.user.CustomItem;
import com.greathack.homlin.pojo.user.CustomItemSearchCriteria;

import java.util.List;


/**
 * @author zjb
 * @version 创建时间：2017年3月8日 上午11:22:44
 * 类说明
 */
public interface ICustomItemDAO extends BaseDAO<CustomItem> {

	/**
	 * 删除用户全部自定义项目
	 * @Title : deleteByUserId
	 * @Description: 删除用户全部自定义项目
	 * @version 删除用户全部自定义项目
	 * @param usrrId
	 * @return 
	 * @throws
	 * @author zjb
	 * @date 2017年3月8日
	 */
	int deleteByUserId(Long usrrId);
	
	/**
	 * 搜索用户自定义项目
	 * @Title : searchCustomItems
	 * @Description: 搜索用户自定义项目
	 * @version 搜索用户自定义项目
	 * @param criteria
	 * @return 
	 * @throws
	 * @author zjb
	 * @date 2017年3月8日
	 */
	List<CustomItem> search(CustomItemSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(CustomItemSearchCriteria criteria);
}
