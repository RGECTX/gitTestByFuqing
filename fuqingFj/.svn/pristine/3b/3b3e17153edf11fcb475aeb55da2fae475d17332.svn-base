package com.greathack.homlin.daointerface.innerMessage;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.innerMessage.InnerMessage;
import com.greathack.homlin.pojo.innerMessage.InnerMessageSearchCriteria;

import java.util.List;

public interface IInnerMessageDAO extends BaseDAO<InnerMessage> {
	
	/**
	 * 根据关键字查找信息
	 */	
	List<InnerMessage> search(InnerMessageSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(InnerMessageSearchCriteria criteria);
	
	/**
	 * 清空地址
	 * @param userId
	 *//*
	void clean(String userId);*/

}
