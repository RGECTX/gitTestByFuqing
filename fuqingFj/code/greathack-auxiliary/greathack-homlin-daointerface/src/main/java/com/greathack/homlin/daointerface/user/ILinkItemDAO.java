package com.greathack.homlin.daointerface.user;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.user.LinkItem;
import com.greathack.homlin.pojo.user.LinkItemSearchCriteria;

import java.util.List;


/**
 * 
 * 类说明 :
 * 
 * @author 作者 wbc
 * @version 创建时间：2017-2-23 下午4:39:36
 * 
 */
public interface ILinkItemDAO extends BaseDAO<LinkItem> {

    /**
     * 根据条件查询 联系项目 列表
     * 
     * @param criteria
     * @return 联系项目 列表
     */
    List<LinkItem> search(LinkItemSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(LinkItemSearchCriteria criteria);

    /**
     * 根据itemTypeId查 的联系项目列表
     * 
     * @param itemTypeId
     * @return
     */
    List<LinkItem> findByItemTypeId(Long itemTypeId);

    /**
     * 根据userId删除 的联系项目列表
     * 
     * @param userId
     */
    void deleteByUserId(long userId);
}
