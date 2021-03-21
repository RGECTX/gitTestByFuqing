package com.greathack.homlin.service;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.SearchResult;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.pojo.permission.PowerItemSearchCriteria;
import com.greathack.homlin.serviceinterface.IAdminPowerItemService;
import com.greathack.homlin.serviceinterface.permission.IPowerItemService;
import com.greathack.homlin.system.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminPowerItemService implements IAdminPowerItemService {
    private static Logger logger = LoggerFactory.getLogger(AdminPowerItemService.class);

    private static String ADMIN_PERMISSION_APP_CODE = SystemConfig.getConfigData("ADMIN_PERMISSION_APP_CODE");

	@Autowired
    private IPowerItemService powerItemService;
    @Override
    public PowerItem add(PowerItem powerItem) throws ServiceException {
        if(powerItem.getPowerCode()==null){
            throw new ServiceException(230001,"INVALID_PARAMS");
        }
        powerItem.setAppCode(ADMIN_PERMISSION_APP_CODE);
    	return powerItemService.add(powerItem);
    }

    @Override
    public void delete(long powerItemId) throws ServiceException {
    	powerItemService.delete(powerItemId);
    }

    @Override
    public void mdfy(PowerItem powerItem) throws ServiceException {
    	powerItemService.mdfy(powerItem);
    }

    @Override
    public PowerItem getPowerItem(long powerItemId) throws ServiceException {
    	return powerItemService.getPowerItem(powerItemId);
    }

    @Override
    public PowerItem getPowerItem(String powerCode) throws ServiceException {
        return powerItemService.getPowerItem(ADMIN_PERMISSION_APP_CODE,powerCode);
    }

    @Override
    public SearchResult<PowerItem> search(PowerItemSearchCriteria criteria) {
        SearchResult<PowerItem> searchResult=new SearchResult<PowerItem>();
        searchResult.setRecordCount(0);
        searchResult.setPage(Math.floorDiv(criteria.getStartLine(), criteria.getPageSize())+1);
        searchResult.setPageSize(criteria.getPageSize());
        criteria.setAppCode(ADMIN_PERMISSION_APP_CODE);
        List<PowerItem> adminPowerItemList=powerItemService.search(criteria);
        long recordCount=powerItemService.getSearchResultCount(criteria);
        searchResult.setRecordCount(recordCount);
        searchResult.setRecords(adminPowerItemList);
        return searchResult;
    }

}
