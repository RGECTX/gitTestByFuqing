package com.greathack.homlin.serviceinterface.user;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.CustomItem;
import com.greathack.homlin.pojo.user.CustomItemSearchCriteria;

import java.util.List;


/**
 * @author zjb
 * @version 创建时间：2017年3月8日 上午10:51:01
 * 用户自定义项目
 */
public interface ICustomItemService {

	/**
	 * 添加用户自定义项目
	 * @Title : 添加用户自定义项目
	 * @Description: TODO
	 * @version 添加用户自定义项目
	 * @param customItem
	 * @return
	 * @throws ServiceException
	 * @throws
	 * @author zjb
	 * @date 2017年3月8日
	 */
	public CustomItem addCustomItem(CustomItem customItem) throws ServiceException;
	
	/**
	 * 删除用户自定义项目
	 * @Title : delCustomItem
	 * @Description: 删除用户自定义项目
	 * @version
	 * @param customItemId 
	 * @throws
	 * @author zjb
	 * @date 2017年3月8日
	 */
	public void delCustomItem(long customItemId);
	/**
	 * 删除用户全部自定义项目
	 * @Title : delUserCustomItems
	 * @Description: 删除用户全部自定义项目
	 * @version 删除用户全部自定义项目
	 * @param userId 
	 * @throws
	 * @author zjb
	 * @date 2017年3月8日
	 */
	public void delUserCustomItems(long userId) throws ServiceException;
	
	/**
	 * 修改用户自定义项目
	 * @Title : mdfyCustomItem
	 * @Description: 修改用户自定义项目
	 * @version 修改用户自定义项目
	 * @param linkItem
	 * @throws ServiceException
	 * @throws
	 * @author zjb
	 * @date 2017年3月8日
	 */
	public void mdfyCustomItem(CustomItem customItem) throws ServiceException;
	
	/**
	 * 获取用户自定义项目
	 * @Title : getCustomItem
	 * @Description: 获取用户自定义项目
	 * @version 获取用户自定义项目
	 * @param customItemId
	 * @return 
	 * @throws
	 * @author zjb
	 * @date 2017年3月8日
	 */
	public CustomItem getCustomItem(long customItemId);
	

	public List<CustomItem> searchCustomItems(CustomItemSearchCriteria criteria);
	
	public long getSearchResultCount(CustomItemSearchCriteria criteria);
}
