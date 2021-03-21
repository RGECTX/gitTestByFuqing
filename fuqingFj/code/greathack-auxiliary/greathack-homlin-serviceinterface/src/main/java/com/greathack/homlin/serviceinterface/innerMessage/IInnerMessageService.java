package com.greathack.homlin.serviceinterface.innerMessage;

import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.innerMessage.InnerMessage;
import com.greathack.homlin.pojo.innerMessage.InnerMessageSearchCriteria;

import java.util.List;


public interface IInnerMessageService {
	
	/**
	 * 
	 * @Title: addInnerMessage
	 * @Description: 添加
	 * @param innerMessage
	 * @return
	 * @throws ServiceException
	 * @author greathack
	*/
	InnerMessage addInnerMessage(InnerMessage innerMessage) throws ServiceException;
	
	/**
	 * 
	 * @Title: delInnerMessage
	 * @Description: 删除
	 * @param innerMessageId
	 * @author greathack
	*/
	void delInnerMessage(long innerMessageId);
	
	/**
	 * 
	 * @Title: mdfyInnerMessage
	 * @Description: 修改
	 * @param innerMessage
	 * @throws ServiceException
	 * @author greathack
	*/
	void mdfyInnerMessage(InnerMessage innerMessage) throws ServiceException;
	
	
	/**
	 * 
	 * @Title: getInnerMessage
	 * @Description: 获取
	 * @param innerMessageId
	 * @return
	 * @author greathack
	*/
	InnerMessage getInnerMessage(long innerMessageId);
	
	/**
	 * 
	 * @Title: search
	 * @Description: 搜索
	 * @param criteria
	 * @return
	 * @author greathack
	 * @throws ServiceException
	*/
	List<InnerMessage> search(InnerMessageSearchCriteria criteria) ;
	
	long getSearchResultCount(InnerMessageSearchCriteria criteria);

}
