package com.greathack.homlin.service.user;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.user.ILinkItemDAO;
import com.greathack.homlin.daointerface.user.IUserDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.LinkItem;
import com.greathack.homlin.pojo.user.LinkItemSearchCriteria;
import com.greathack.homlin.serviceinterface.user.ILinkItemService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类说明 :
 * 
 * @author 作者 wbc
 * @version 创建时间：2017-2-23 下午4:37:14
 * 
 */
public class LinkItemService implements ILinkItemService {

	private static Logger logger = LoggerFactory.getLogger(LinkItemService.class);

	// 注入DAO
	private ILinkItemDAO linkItemDAO = (ILinkItemDAO) DAOFactory.createDAO("ILinkItemDAO");

	// 注入DAO
	private IUserDAO userDAO = (IUserDAO) DAOFactory.createDAO("IUserDAO");

	public LinkItem addLinkItem(LinkItem linkItem) throws ServiceException {
		if (linkItem == null) {
			return null;
		}

		if (Validation.isEmpty(linkItem.getAppCode())) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}

		// 如果userId或itemTypeId为空则抛出“参数异常”异常
		if (linkItem.getUserId() == null || linkItem.getUserId() < 0) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		if (linkItem.getItemTypeId() == null || linkItem.getItemTypeId() < 0) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		
		//用户不存在
		if(!UserService.isExist(linkItem.getUserId())){
			throw new ServiceException(30005, "USER_DOES_NOT_EXIST");
		}		

		// 设置为空时的默认
		if (linkItem.getIsDefault() == null) {
			linkItem.setIsDefault(false);
		}
		

		linkItem.setCreateTime(Common.getCurrentTime());
		linkItem.setLinkItemId(IdCreator.createId("LinkItem"));
		linkItemDAO.add(linkItem);

		// 设置默认为真时,同一个项目类型只能有一个默认，设为默认后，其他的同一类型的联系项目全部变为非默认
		if (linkItem.getIsDefault()) {
			setDefault(linkItem.getLinkItemId());
		}
		
		return linkItem;
	}

	public void delLinkItem(long linkItemId) {
		linkItemDAO.delete(linkItemId);
	}

	public void delUserLinkItems(long userId) throws ServiceException {
		//用户不存在
		if(!UserService.isExist(userId)){
			throw new ServiceException(30005, "USER_DOES_NOT_EXIST");
		}
		linkItemDAO.deleteByUserId(userId);
	}

	public void mdfyLinkItem(LinkItem linkItem) throws ServiceException {
		
		if (Validation.isEmpty(linkItem.getAppCode())) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		
		LinkItem result = linkItemDAO.findById(linkItem.getLinkItemId());

		// 如果用户联系项目不存在则抛出“用户联系项目不存在”异常
		if (result == null) {
			throw new ServiceException(30013, "LINK_ITEM_NOT_EXIST");
		}
		
		linkItemDAO.update(linkItem);

		// 设置默认为真时,同一个项目类型只能有一个默认，设为默认后，其他的同一类型的联系项目全部变为非默认
		if (linkItem.getIsDefault()) {
			setDefault(linkItem.getLinkItemId());
		}
	}

	public void setDefault(long linkItemId) throws ServiceException {
		LinkItem li = linkItemDAO.findById(linkItemId);
		// 如果用户联系项目不存在则抛出“用户联系项目不存在”异常
		if (li == null) {
			throw new ServiceException(30013, "LINK_ITEM_NOT_EXIST");
		}

		// 同一个项目类型只能有一个默认,设为默认后，其他的同一类型的联系项目全部变为非默认
		LinkItem condition= new LinkItem();
		condition.setUserId(li.getUserId());
		condition.setItemTypeId(li.getItemTypeId());
		List<LinkItem> list = linkItemDAO.findByExample(condition);

		for (int i = 0; i < list.size(); i++) {
			LinkItem linkItem = list.get(i);
			linkItem.setIsDefault(false);
			linkItemDAO.update(linkItem);
		}

		li.setIsDefault(true);

		linkItemDAO.update(li);

	}

	public LinkItem getLinkItem(long linkItemId) {
		return  linkItemDAO.findById(linkItemId);
	}

	public List<LinkItem> searchLinkItems(LinkItemSearchCriteria criteria) {
		List<LinkItem>  linkItemList=new ArrayList<LinkItem>();
		if (criteria == null) {
			return linkItemList;
		}
		return linkItemDAO.search(criteria);
	}

	public long getSearchResultCount(LinkItemSearchCriteria criteria) {
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
				count=Long.toString(linkItemDAO.getSearchResultCount(criteria));
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
