package com.greathack.homlin.serviceinterface.user;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.AddressItem;
import com.greathack.homlin.pojo.user.AddressItemSearchCriteria;

import java.util.List;


public interface IAddressItemService {
	
	/**
	 * 添加用户地址项目<br>
	 * 如果userId为空则抛出“参数异常”异常
	 * @param addressItem 要添加的用户地址项目
	 * @return
	 * @throws ServiceException
	 */
	public AddressItem addAddressItem(AddressItem addressItem) throws ServiceException;
	
	/**
	 * 删除用户地址项目
	 * @param addressItemId 地址项目ID
	 */
	public void delAddressItem(long addressItemId);
	
	/**
	 * 删除用户全部地址项目<br>
	 * 如果用户不存在则抛出“用户不存在”异常；
	 * @param userId 用户ID
	 */
	public void delUserAddressItems(long userId) throws ServiceException;
	
	/**
	 * 修改用户地址项目<br>
	 * 修改除了addressItemId,outId,userId等属性以外的其它信息，属性为空的不参与修改<br>
	 * 如果用户地址项目不存在则抛出“用户地址项目不存在”异常；
	 * @param addressItem 要修改的用户地址项目
	 * @throws ServiceException
	 */
	public void mdfyAddressItem(AddressItem addressItem) throws ServiceException;
	
	/**
	 * 设为默认<br>
	 * 同一个用户只能有一个默认，设为默认后，其他的地址项目全部变为非默认<br>
	 * 如果用户地址项目不存在则抛出“用户地址项目不存在”异常；
	 * @param addressItemId 地址项目ID
	 * @throws ServiceException
	 */
	public void setDefault(long addressItemId) throws ServiceException;
	
	/**
	 * 获取用户地址项目
	 * @param addressItemId 地址项目ID
	 * @return 用户地址项目
	 */
	public AddressItem getAddressItem(Long addressItemId);
	
	/**
	 * 搜索用户地址项目
	 * @param criteria 搜索条件
	 * @return 用户地址项目列表
	 */
	public List<AddressItem> searchAddressItems(AddressItemSearchCriteria criteria);

	public long getSearchResultCount(AddressItemSearchCriteria criteria);
}
