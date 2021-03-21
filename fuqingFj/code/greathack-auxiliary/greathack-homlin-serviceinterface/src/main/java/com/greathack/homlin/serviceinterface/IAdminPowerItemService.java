package com.greathack.homlin.serviceinterface;


import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.pojo.permission.PowerItemSearchCriteria;

public interface IAdminPowerItemService {
    
    PowerItem add(PowerItem powerItem) throws ServiceException;
    
    void delete(long powerItemId) throws ServiceException;
    
    void mdfy(PowerItem powerItem) throws ServiceException;
    
    PowerItem getPowerItem(long powerItemId) throws ServiceException;

    PowerItem getPowerItem(String powerCode) throws ServiceException;
    
    SearchResult<PowerItem> search(PowerItemSearchCriteria criteria);
}
