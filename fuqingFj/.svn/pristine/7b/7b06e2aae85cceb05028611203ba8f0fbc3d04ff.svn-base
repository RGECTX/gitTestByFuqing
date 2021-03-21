/**
 * 
 */
package com.greathack.homlin.service.user;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.user.IIdcardDAO;
import com.greathack.homlin.daointerface.user.IUserDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.Idcard;
import com.greathack.homlin.pojo.user.IdcardSearchCriteria;
import com.greathack.homlin.pojo.user.User;
import com.greathack.homlin.serviceinterface.user.IIdcardService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author greathack
 *
 */
public class IdcardService implements IIdcardService {

	private static Logger logger = LoggerFactory.getLogger(IdcardService.class);
	private IIdcardDAO iIdcardDAO = (IIdcardDAO) DAOFactory.createDAO("IIdcardDAO");
	private IUserDAO userDAO = (IUserDAO) DAOFactory.createDAO("IUserDAO");
	
	public Idcard addIdcard(Idcard idcard) throws ServiceException {
		if (Validation.isEmpty(idcard.getAppCode())) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		idcard.setIdcardId(IdCreator.createId("Idcard"));
		idcard.setCreateTime(Common.getCurrentTime());
		if(idcard.getIsDefault()==null){
			idcard.setIsDefault(false);//默认为false
		}
		if(!UserService.isExist(idcard.getUserId())){
			logger.info("用户不存在");
			throw new ServiceException(30005,"USER_DOES_NOT_EXIST");
		}
		if(idcard.getIsDefault())
		{
			Idcard condition=new Idcard();
			condition.setAppCode(idcard.getAppCode());
			condition.setUserId(idcard.getUserId());
			List<Idcard> idcardList = iIdcardDAO.findByExample(condition);
			for (Idcard idcard2 : idcardList) {
				idcard2.setIsDefault(false);
				iIdcardDAO.update(idcard2);
			}
		}
			
		iIdcardDAO.add(idcard);
		return idcard;
	}

	public void delIdcard(long idcardId) {
		iIdcardDAO.delete(idcardId);
	}

	public void delUserIdcard(long userId) throws ServiceException {
		if(!UserService.isExist(userId)){
			logger.info("用户不存在");
			throw new ServiceException(30005,"USER_DOES_NOT_EXIST");
		}
		User user = (User) userDAO.findById(userId);
		iIdcardDAO.delUserIdcard(userId);
	}
	/**
	 * 修改用户信息：说明一个用户只能有一个默认的状态
	 */
	public void mdfyIdcard(Idcard idcard) throws ServiceException {
		if (Validation.isEmpty(idcard.getAppCode())) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		//获取用户的状态信息
		if(idcard.getIsDefault())
		{
			Idcard condition=new Idcard();
			condition.setAppCode(idcard.getAppCode());
			condition.setUserId(idcard.getUserId());
			List<Idcard> idcardList = iIdcardDAO.findByExample(condition);
			for (Idcard idcard2 : idcardList) {
				idcard2.setIsDefault(false);
				iIdcardDAO.update(idcard2);
			}
		}
		
		iIdcardDAO.update(idcard);
	}

	public void setDefault(long idcardId) throws ServiceException {
		Idcard idcard = iIdcardDAO.findById(idcardId);
		if(idcard==null){
			throw new ServiceException(30005, "USER_DOES_NOT_EXIST");
		}else{
			Idcard condition=new Idcard();
			condition.setAppCode(idcard.getAppCode());
			condition.setUserId(idcard.getUserId());
			List<Idcard> idcardList = iIdcardDAO.findByExample(condition);
			for (Idcard idcard2 : idcardList) {
				idcard2.setIsDefault(false);
				iIdcardDAO.update(idcard2);
			}
		}
		idcard.setIsDefault(true);
		iIdcardDAO.update(idcard);
	}

	public Idcard getIdcard(long idcardId) {
		return iIdcardDAO.findById(idcardId);
	}

	public List<Idcard> searchIdcards(IdcardSearchCriteria criteria) {
		List<Idcard> idcardList=new ArrayList<Idcard>();
		if(criteria==null){
			return idcardList;
		}
		return iIdcardDAO.search(criteria);
	}

	public long getSearchResultCount(IdcardSearchCriteria criteria) {
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
				count=Long.toString(iIdcardDAO.getSearchResultCount(criteria));
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
