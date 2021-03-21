/**
 * 
 */
package com.greathack.homlin.serviceinterface.user;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.User;
import com.greathack.homlin.pojo.user.UserSearchCriteria;

import java.util.List;


/**
 * @author greathack
 * 
 */
public interface IUserService {

    /**
     * 添加用户基本信息，如果appCode为空则抛出“参数异常”异常
     * 
     * @param user
     *            要添加的用户信息
     * @return 添加好的用户信息
     * @throws ServiceException
     */
    public User addUser(User user) throws ServiceException;

    /**
     * 删除用户基本信息，设置该用户基本信息状态为删除
     * 
     * @param userId
     *            用户ID
     */
    public void delUser(long userId);   

    /**
     * 修改用户基本信息 修改除了userId,outId,appCode, userState等属性以外的其它信息，属性为空的不参与修改<br>
     * 如果用户不存在则抛出“用户不存在”异常；
     * 
     * @param user
     *            修改的用户信息
     * @throws ServiceException
     */
    public void mdfyUser(User user) throws ServiceException;

    /**
     * 修改用户状态
     * 
     * @param userId
     *            用户ID
     * @param userState
     *            用户状态，1:正常，2：锁定，4：删除，8：预留状态1，16：预留状态2，32：预留状态3，64：预留状态4
     * @throws ServiceException
     */
    public void mdfyUserState(long userId, int userState)
	    throws ServiceException;

    /**
     * 获取用户基本信息
     * 
     * @param userId
     *            用户ID
     * @return 用户基本信息
     */
    public User getUser(long userId);
    
	/**
	 * 根据用户ID列表查询用户列表
	 * @param userIds ID列表
	 * @return
	 */
	List<User> getUserListByIds(List<Long> userIds);


    public List<User> findByExample(User instance);

    /**
     * 搜索用户
     * 
     * @param criteria
     *            搜索条件
     * @return 搜索到的用户列表
     */
    public List<User> searchUsers(UserSearchCriteria criteria);
   
    public long getSearchResultCount(UserSearchCriteria criteria);
}
