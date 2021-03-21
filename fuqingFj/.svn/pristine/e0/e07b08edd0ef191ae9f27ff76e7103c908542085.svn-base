package com.greathack.homlin.service.innerMessage;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.innerMessage.IInnerMessageDAO;
import com.greathack.homlin.daointerface.innerMessage.IReceiveDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.innerMessage.InnerMessage;
import com.greathack.homlin.pojo.innerMessage.InnerMessageSearchCriteria;
import com.greathack.homlin.pojo.innerMessage.Receive;
import com.greathack.homlin.serviceinterface.innerMessage.IInnerMessageService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Utils;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class InnerMessageService implements IInnerMessageService {
	
	private static Logger logger = LoggerFactory.getLogger(InnerMessageService.class);
	private static IInnerMessageDAO innerMessageDAO = (IInnerMessageDAO) DAOFactory.createDAO("IInnerMessageDAO");
	private static IReceiveDAO receiveDAO = (IReceiveDAO) DAOFactory.createDAO("IReceiveDAO");

	public InnerMessage addInnerMessage(InnerMessage innerMessage) throws ServiceException {
		if(innerMessage==null){
			return null;
		}
		if(Validation.isEmpty(innerMessage.getAppCode()) ){
			throw new ServiceException(260001,"INVALID_PARAMS");
		}
		if(innerMessage.getMsgState()==null){
			innerMessage.setMsgState(2);
		}
		ArrayList<Long> longRecountToArrayList = Utils.longRecountToArrayList(innerMessage.getMsgState());
		if(!longRecountToArrayList.contains(Long.valueOf(innerMessage.getMsgState()))){
			logger.info("MsgState不在取值范围");
			throw new ServiceException(170001,"INVALID_PARAMS");
		}
		innerMessage.setInnerMessageId(IdCreator.createId("innerMessage"));

		//添加收件人
		innerMessage.setCreateTime(Common.getCurrentTime());
		if(innerMessage.getReceiveList()!=null){
			for(Receive receive:innerMessage.getReceiveList()){
				receive.setAppCode(innerMessage.getAppCode());
				receive.setInnerMessageId(innerMessage.getInnerMessageId());
				receive.setReceiveState(1);
				receive.setReceiveId(IdCreator.createId("Receive"));
				receiveDAO.add(receive);
			}
		}

		innerMessageDAO.add(innerMessage);
		return innerMessage;
	}


	public void delInnerMessage(long innerMessageId) {
		receiveDAO.empty(innerMessageId);
		innerMessageDAO.delete(innerMessageId);
		
	}

	
	public void mdfyInnerMessage(InnerMessage innerMessage) throws ServiceException {
		
		InnerMessage temp=innerMessageDAO.findById(innerMessage.getInnerMessageId());
		if(temp==null){
			throw new ServiceException(260002,"ADDRESS__NOT_EXIST");
		}
		innerMessage.setAppCode(temp.getAppCode());

		receiveDAO.empty(innerMessage.getInnerMessageId());
		//添加收件人
		innerMessage.setCreateTime(Common.getCurrentTime());
		if(innerMessage.getReceiveList()!=null){
			for(Receive receive:innerMessage.getReceiveList()){
				receive.setAppCode(innerMessage.getAppCode());
				receive.setInnerMessageId(innerMessage.getInnerMessageId());
				receive.setReceiveState(1);
				receive.setReceiveId(IdCreator.createId("Receive"));
				receiveDAO.add(receive);
			}
		}

		innerMessageDAO.update(innerMessage);
		
	}


   
	public InnerMessage getInnerMessage(long innerMessageId) {
		InnerMessage innerMessage=innerMessageDAO.findById(innerMessageId);
		Receive cond=new Receive();
		cond.setAppCode(innerMessage.getAppCode());
		cond.setInnerMessageId(innerMessage.getInnerMessageId());
		List<Receive> receiveList=receiveDAO.findByExample(cond);
		innerMessage.setReceiveList(receiveList);
		return innerMessage;

	}

	public List<InnerMessage> search(InnerMessageSearchCriteria criteria){
		List<InnerMessage> innerMessageList=innerMessageDAO.search(criteria);
		for(InnerMessage innerMessage : innerMessageList){
			Receive cond=new Receive();
			cond.setAppCode(innerMessage.getAppCode());
			cond.setInnerMessageId(innerMessage.getInnerMessageId());
			List<Receive> receiveList=receiveDAO.findByExample(cond);
			innerMessage.setReceiveList(receiveList);
		}
		return innerMessageList;
	}

	public long getSearchResultCount(InnerMessageSearchCriteria criteria) {
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
				count=Long.toString(innerMessageDAO.getSearchResultCount(criteria));
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
