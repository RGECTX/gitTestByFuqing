package com.greathack.homlin.service.user;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.user.IAddressItemDAO;
import com.greathack.homlin.daointerface.user.IUserDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.AddressItem;
import com.greathack.homlin.pojo.user.AddressItemSearchCriteria;
import com.greathack.homlin.pojo.user.User;
import com.greathack.homlin.serviceinterface.user.IAddressItemService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;


public class AddressItemService implements IAddressItemService {
	
	private static Logger logger = LoggerFactory.getLogger(AddressItemService.class);
	
	private IAddressItemDAO addressItemDAO = (IAddressItemDAO) DAOFactory.createDAO("IAddressItemDAO");
	private IUserDAO userDAO = (IUserDAO) DAOFactory.createDAO("IUserDAO");
	
	public AddressItem addAddressItem(AddressItem addressItem)
			throws ServiceException {
		if (Validation.isEmpty(addressItem.getAppCode())) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		if(addressItem.getUserId() == null){
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		boolean isDefault = false;
		addressItem.setAddressItemId(IdCreator.createId("AddressItem"));
		if(addressItem.getIsDefault() != null){
			isDefault = addressItem.getIsDefault();
		}
		addressItem.setCreateTime(Common.getCurrentTime());
		addressItem.setIsDefault(isDefault);
		addressItemDAO.add(addressItem);
		
		if(addressItem.getIsDefault()==true){
		    setDefault(addressItem.getAddressItemId());
		}
		return addressItem;
	}

	public void delAddressItem(long addressItemId) {
		addressItemDAO.delete(addressItemId);
	}

	public void delUserAddressItems(long userId) throws ServiceException {
		 User user = userDAO.findById(userId);
		 if(null == user){
			 throw new ServiceException(30005, "USER_DOES_NOT_EXIST");
		 }
		 addressItemDAO.deleteByUserId(userId);
	}

	public void mdfyAddressItem(AddressItem addressItem)
			throws ServiceException {
		Long id = addressItem.getAddressItemId();
		AddressItem addressItemById = null;
		if(null != id && 0 != id){
			addressItemById = addressItemDAO.findById(id);
		}
		
		if(addressItemById == null){
			throw new ServiceException(30006, "USER_ADDRESS_ITEM_NOT_EXIST");
		}
		addressItemDAO.update(addressItem);
		
		if(addressItem.getIsDefault()==true){
            setDefault(addressItem.getAddressItemId());
        }
	}

	@Transactional
	public void setDefault(long addressItemId) throws ServiceException {
		AddressItem addressItem = addressItemDAO.findById(addressItemId);
		if(addressItem == null){
			throw new ServiceException(30006, "USER_ADDRESS_ITEM_NOT_EXIST");
		}
		if(addressItem.getIsDefault()==false){
    		addressItemDAO.setNotDefault(addressItem.getUserId());
    		addressItemDAO.setDefault(addressItemId);
		}
	}

	public AddressItem getAddressItem(Long addressItemId) {
		return addressItemDAO.findById(addressItemId);
	}

	public List<AddressItem> searchAddressItems(AddressItemSearchCriteria criteria){
		List<AddressItem> addressItemList=new ArrayList<AddressItem>();
		if(criteria==null){
			return addressItemList;
		}
		return addressItemDAO.search(criteria);
	}



	public long getSearchResultCount(AddressItemSearchCriteria criteria) {
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
				count=Long.toString(addressItemDAO.getSearchResultCount(criteria));
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
