/**
 * 
 */
package com.greathack.homlin.daointerface.user;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.user.User;
import com.greathack.homlin.pojo.user.UserSearchCriteria;

import java.util.List;


/**
 * @author greathack
 *
 */
public interface IUserDAO extends BaseDAO<User> {
	
	
	/**
	 * 根据用户ID列表查询用户列表
	 * @param userIds ID列表
	 * @return
	 */
	List<User> getUserListByIds(List<Long> userIds);
	
	/**
	 * 根据关键字查找用户的信息
	 * 2017.2.23
	 * @param criteria
	 * @author lbb
	 */	
	List<User> search(UserSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(UserSearchCriteria criteria);

}
