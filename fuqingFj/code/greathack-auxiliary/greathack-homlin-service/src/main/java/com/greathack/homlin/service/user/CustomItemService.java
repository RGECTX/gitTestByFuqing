package com.greathack.homlin.service.user;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.user.ICustomItemDAO;
import com.greathack.homlin.daointerface.user.IUserDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.CustomItem;
import com.greathack.homlin.pojo.user.CustomItemSearchCriteria;
import com.greathack.homlin.pojo.user.User;
import com.greathack.homlin.serviceinterface.user.ICustomItemService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjb
 * @version 创建时间：2017年3月8日 上午11:18:42
 * 类说明
 */
public class CustomItemService implements ICustomItemService {

	private ICustomItemDAO customItemDAO = (ICustomItemDAO) DAOFactory.createDAO("ICustomItemDAO");
	private IUserDAO userDAO = (IUserDAO) DAOFactory.createDAO("IUserDAO");
	private static Logger logger = LoggerFactory.getLogger(LinkItemService.class);
	
	public CustomItem addCustomItem(CustomItem customItem)
			throws ServiceException {
		if (Validation.isEmpty(customItem.getAppCode())) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		if(customItem.getUserId() == null){
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		if(customItem.getItemName() == null){
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		customItem.setCustomItemId(IdCreator.createId("CustomItem"));
		customItem.setCreateTime(Common.getCurrentTime());
		
		customItemDAO.add(customItem);
		return customItem;
	}

	public void delCustomItem(long customItemId) {
		customItemDAO.delete(customItemId);
	}

	public void delUserCustomItems(long userId) throws ServiceException {
		User user = (User) userDAO.findById(userId);
		if(user == null){
			throw new ServiceException(30005, "USER_DOES_NOT_EXIST");
		}
		customItemDAO.deleteByUserId(user.getUserId());
	}

	public void mdfyCustomItem(CustomItem customItem) throws ServiceException {
		if(customItem==null){
			throw new ServiceException(30007, "USER_DEFINED_ITEM_NOT_EXIST");
		}
		CustomItem localCustomeriItem = customItemDAO.findById(customItem.getCustomItemId());
		if(localCustomeriItem == null){
			throw new ServiceException(30007, "USER_DEFINED_ITEM_NOT_EXIST");
		}		
		customItemDAO.update(customItem);
	}

	public CustomItem getCustomItem(long customItemId) {
		return (CustomItem) customItemDAO.findById(customItemId);
	}

	public List<CustomItem> searchCustomItems(CustomItemSearchCriteria criteria) {
		List<CustomItem> customItemList = new ArrayList<CustomItem>();
		if(criteria==null){
			return customItemList;
		}
		return customItemDAO.search(criteria);
	}

	public long getSearchResultCount(CustomItemSearchCriteria criteria) {
		if(criteria==null){
			return 0;
		}		
		String key=Integer.toString(criteria.hashCode());
		String count="0";
		Jedis jedis=null;
		try {
			jedis= SystemConfig.getJedisPool().getResource();
			if(jedis.exists(key)){
				count=jedis.get(key);
				//重新设置超时
				jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
			}else{
				count=Long.toString(customItemDAO.getSearchResultCount(criteria));
				jedis.set(key, count);
				//设置超时
				jedis.expire(key, Integer.parseInt(SystemConfig.getConfigData("RESULT_COUNT_EXPIRE")));
			}			
		} catch (Exception e) {
			logger.debug("redis有异常");
		}finally{
			if(jedis!=null){
				jedis.close();
			}			
		}	
		return Long.parseLong(count);
	}

}
