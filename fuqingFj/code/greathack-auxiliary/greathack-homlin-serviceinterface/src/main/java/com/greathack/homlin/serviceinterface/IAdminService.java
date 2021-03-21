/**
 * 
 */
package com.greathack.homlin.serviceinterface;


import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.Admin;
import com.greathack.homlin.pojo.Admin.State;
import com.greathack.homlin.pojo.AdminListResult;
import com.greathack.homlin.pojo.AdminSearchCriteria;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.login.LoginInstance;

import java.util.List;

/**
 * @author greathack
 * 
 */
public interface IAdminService {
    
    Admin add(Admin admin) throws ServiceException;
    
    void delete(Long adminId) throws ServiceException;
    
    void mdfy(Admin admin) throws ServiceException;
    
    void mdfyPwd(String loginName, String newPwd) throws ServiceException;
    
   // void mdfyState(String  adminId,State adminState) throws ServiceException;
    
    /**	
     * 通过用户名密码登录
     * @Title: login
     * @Description: TODO
     * @param loginName
     * @param password
     * @return
     * @author greathack
    */
    LoginInstance login(String loginName, String password) throws ServiceException;
    
    /**
     * 通过手机验证码登录
     * @Title: loginByMobile
     * @Description: TODO
     * @param mobile
     * @return
     * @author greathack
    */
    LoginInstance loginByMobile(String mobile) throws ServiceException;
    
    void logout(String loginCode) throws ServiceException;
    
    Admin getAdmin(String loginCode);
    
    Admin getAdminById(Long adminId);

    /**
     * 搜索管理员
     * 
     * @param criteria  搜索条件
     * @return 搜索到的管理员列表
     */
    public SearchResult<Admin> searchAdmins(AdminSearchCriteria criteria);

	void mdfyState(Long adminId, State adminState) throws ServiceException;
	
	/**
	 * 根据用户ID列表获取用户列表
	 * @param userList
	 * @return
	 */
	AdminListResult getListByUserIds(List<Long> userList);

    List<Admin> getListByUserIdcard(String idcard);
}
