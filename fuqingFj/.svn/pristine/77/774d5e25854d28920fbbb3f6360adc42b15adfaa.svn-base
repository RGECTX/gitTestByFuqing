package com.greathack.homlin.daointerface.permission;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.permission.PowerItem;
import com.greathack.homlin.pojo.permission.PowerItemSearchCriteria;

import java.util.List;


public interface IPowerItemDAO extends BaseDAO<PowerItem> {

	
	/**
	 * 查找全部权限项目
	 * @return 全部权限项目列表
	 */
	List<PowerItem> findAll(String appCode);
	
	   /**
     * 根据关键字查找信息
     */ 
    List<PowerItem> search(PowerItemSearchCriteria criteria);
    
    /**
     * 
     * @Title: getSearchRecordCount
     * @Description: 获取搜索结果的总记录数
     * @param criteria 搜索条件
     * @return 总记录数
     * @author greathack
     * @date 2017年4月26日
    */
    long getSearchResultCount(PowerItemSearchCriteria criteria);

}
