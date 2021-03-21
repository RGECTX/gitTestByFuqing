package com.greathack.homlin.serviceinterface.innerMessage;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.innerMessage.Receive;
import com.greathack.homlin.pojo.innerMessage.ReceiveSearchCriteria;

import java.util.List;


public interface IReceiveService {
	
	/**
	 * 
	 * @Title: addReceive
	 * @Description: 添加
	 * @param receive
	 * @return
	 * @throws ServiceException
	 * @author greathack
	*/
	Receive addReceive(Receive receive) throws ServiceException;
	
	/**
	 * 
	 * @Title: delReceive
	 * @Description: 删除
	 * @param receiveId
	 * @author greathack
	*/
	void delReceive(long receiveId);

	void read(long receiveId);
	/**
	 * 
	 * @Title: mdfyReceive
	 * @Description: 修改
	 * @param receive
	 * @throws ServiceException
	 * @author greathack
	*/
	void mdfyReceive(Receive receive) throws ServiceException;
	
	
	/**
	 * 
	 * @Title: getReceive
	 * @Description: 获取
	 * @param receiveId
	 * @return
	 * @author greathack
	*/
	Receive getReceive(long receiveId);
	
	/**
	 * 
	 * @Title: search
	 * @Description: 搜索
	 * @param criteria
	 * @return
	 * @author greathack
	 * @throws ServiceException
	 * @date 2017年4月27日
	*/
	List<Receive> search(ReceiveSearchCriteria criteria) ;
	
	long getSearchResultCount(ReceiveSearchCriteria criteria);
	



}
