package com.greathack.homlin.daointerface.innerMessage;

import com.greathack.homlin.daointerface.system.BaseDAO;
import com.greathack.homlin.pojo.innerMessage.Receive;
import com.greathack.homlin.pojo.innerMessage.ReceiveSearchCriteria;

import java.util.List;

public interface IReceiveDAO extends BaseDAO<Receive> {
	
	/**
	 * 根据关键字查找信息
	 */	
	List<Receive> search(ReceiveSearchCriteria criteria);
	
	/**
	 * 
	 * @Title: getSearchRecordCount
	 * @Description: 获取搜索结果的总记录数
	 * @param criteria 搜索条件
	 * @return 总记录数
	 * @author greathack
	 * @date 2017年4月26日
	*/
	long getSearchResultCount(ReceiveSearchCriteria criteria);
	
	/*
	 * 清空收件人
	 * @param innerMessageId
	 */
	void empty(long innerMessageId);

}
