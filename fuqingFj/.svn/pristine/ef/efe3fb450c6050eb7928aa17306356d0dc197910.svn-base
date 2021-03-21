package com.greathack.homlin.service.innerMessage;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.innerMessage.IReceiveDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.innerMessage.Receive;
import com.greathack.homlin.pojo.innerMessage.ReceiveSearchCriteria;
import com.greathack.homlin.serviceinterface.innerMessage.IReceiveService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Utils;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;


public class ReceiveService implements IReceiveService {
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveService.class);
	private static IReceiveDAO receiveDAO = (IReceiveDAO) DAOFactory.createDAO("IReceiveDAO");

	public Receive addReceive(Receive receive) throws ServiceException {
		if(receive==null){
			return null;
		}
		if(Validation.isEmpty(receive.getAppCode()) ){
			throw new ServiceException(260001,"INVALID_PARAMS");
		}
		if(receive.getReceiveState()==null){
			receive.setReceiveState(2);
		}
		ArrayList<Long> longRecountToArrayList = Utils.longRecountToArrayList(receive.getReceiveState());
		if(!longRecountToArrayList.contains(Long.valueOf(receive.getReceiveState()))){
			logger.info("MsgState不在取值范围");
			throw new ServiceException(170001,"INVALID_PARAMS");
		}
		receive.setReceiveId(IdCreator.createId("receive"));
		receive.setReadTime(Common.getCurrentTime());
		receiveDAO.add(receive);
		return receive;
	}


	public void delReceive(long receiveId) {
		receiveDAO.delete(receiveId);
		
	}


	public void read(long receiveId) {
		Receive temp=receiveDAO.findById(receiveId);
		if(temp.getReceiveState()==1){
			temp.setReceiveState(2);
			temp.setReadTime(Common.getCurrentTime());
			receiveDAO.update(temp);
		}
	}

	
	public void mdfyReceive(Receive receive) throws ServiceException {
		
		Receive temp=receiveDAO.findById(receive.getReceiveId());
		if(temp==null){
			throw new ServiceException(260002,"ADDRESS__NOT_EXIST");
		}
		receive.setAppCode(temp.getAppCode());
		receiveDAO.update(receive);
		
	}


   
	public Receive getReceive(long receiveId) {
		Receive receive=receiveDAO.findById(receiveId);
		return receive;

	}

	public List<Receive> search(ReceiveSearchCriteria criteria){
		List<Receive> receiveList=receiveDAO.search(criteria);
		return receiveList;
	}

	public long getSearchResultCount(ReceiveSearchCriteria criteria) {
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
				count=Long.toString(receiveDAO.getSearchResultCount(criteria));
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
