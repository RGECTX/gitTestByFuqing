package com.greathack.homlin.serviceinterface.permission;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.pojo.permission.PowerItemSearchCriteria;

import java.util.List;


public interface IPowerItemService {

    /**
     * 新增权限项目
     * @param powerItem 要新增的权限项目
     */
    public PowerItem add(PowerItem powerItem) throws ServiceException;


    /**
     * 修改权限项目
     * @param powerItem 修改的权限项目
     */
    public void mdfy(PowerItem powerItem) throws ServiceException;


    /**
     * 删除权限项目
     * @param powerItemId 权限项目ID
     */
    public void delete(long powerItemId);


    /**
     * 获取权限项目
     * @param powerItemId 权限项目ID
     */
    public PowerItem getPowerItem(long powerItemId);


    /**
     * 获取权限项目
     * @param appCode 应用编码
     * @param powerCode 权限编码
     */
    public PowerItem getPowerItem(String appCode, String powerCode);

    /**
     * 获取全部权限项目列表
     * @return 全部权限项目列表
     */
    public List<PowerItem> getAllList(String appCode);
    
    
    /**
     * 
     * @Title: search
     * @Description: 搜索权限项目
     * @param criteria
     * @return
     * @author greathack
     * @throws ServiceException
    */
    List<PowerItem> search(PowerItemSearchCriteria criteria) ;
    
    long getSearchResultCount(PowerItemSearchCriteria criteria);


}