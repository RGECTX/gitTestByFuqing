/**
 * 
 */
package com.greathack.homlin.serviceinterface.user;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.Idcard;
import com.greathack.homlin.pojo.user.IdcardSearchCriteria;

import java.util.List;


/**
 * @author greathack
 *
 */
public interface IIdcardService {
	
	/**
	 * 添加用户身份证件信息，如果userId或idcardType为空则抛出“参数异常”异常
	 * @param idcard 要添加的用户身份证件信息
	 * @return
	 * @throws ServiceException
	 */
	public Idcard addIdcard(Idcard idcard) throws ServiceException;
	
	/**
	 * 删除用户身份证件信息
	 * @param idcardId 证件ID
	 */
	public void delIdcard(long idcardId);
	
	/**
	 * 删除用户全部身份证件信息，如果用户不存在则抛出“用户不存在”异常；
	 * @param userId 用户ID
	 */
	public void delUserIdcard(long userId) throws ServiceException;
	
	/**
	 * 修改用户身份证件信息<br>
	 * 修改除了idcardId,outId,userId等属性以外的其它信息，属性为空的不参与修改<br>
	 * 如果用户身份证件不存在则抛出“用户身份证件不存在”异常；
	 * @param idcard 要修改的用户身份证件信息
	 * @throws ServiceException
	 */
	public void mdfyIdcard(Idcard idcard) throws ServiceException;
	
	/**
	 * 设为默认<br>
	 * 同一个用户只能有一个默认，设为默认后，其他的身份证件全部变为非默认<br>
	 * 如果用户身份证件不存在则抛出“用户身份证件不存在”异常；
	 * @param idcardId 证件ID
	 * @throws ServiceException
	 */
	public void setDefault(long idcardId) throws ServiceException;
	
	/**
	 * 获取用户身份证件信息
	 * @param idcardId 证件ID
	 * @return 用户身份证件信息
	 */
	public Idcard getIdcard(long idcardId);
	
	/**
	 * 搜索用户身份证件
	 * @param criteria 搜索条件
	 * @return 用户身份证件列表
	 */
	public List<Idcard> searchIdcards(IdcardSearchCriteria criteria);
	
	public long getSearchResultCount(IdcardSearchCriteria criteria);

}
