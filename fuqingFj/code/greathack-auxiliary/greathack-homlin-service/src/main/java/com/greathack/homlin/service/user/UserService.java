/**
 * 
 */
package com.greathack.homlin.service.user;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.user.IUserDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.user.User;
import com.greathack.homlin.pojo.user.UserSearchCriteria;
import com.greathack.homlin.serviceinterface.user.IUserService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Utils;
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
public class UserService implements IUserService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	private static IUserDAO userDAO = (IUserDAO) DAOFactory.createDAO("IUserDAO");

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.greathack.user.serviceinterface.IUserService#addUser(com.greathack.user
	 * .pojo.User)
	 */
	public User addUser(User user) throws ServiceException {
		if (Validation.isEmpty(user.getAppCode())) {
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		if (user.getUserState() == null) {
			user.setUserState(User.USER_STATE_NORMAL);
		} else {
			List<Long> userStateList=Utils.longRecountToArrayList(user.getUserState());
			if(!userStateList.contains(Long.valueOf(user.getUserState().toString()))){
				logger.info("userState 不在取值范围内");
				throw new ServiceException(30001, "INVALID_PARAMS");
			}
		}
		if(user.getSex()!=null){
			if(user.getSex()!=1 && user.getSex()!=2){
				logger.info("sex 不在取值范围内");
				throw new ServiceException(30001, "INVALID_PARAMS");
			}
		}
		user.setUserId(IdCreator.createId("User"));
		user.setCreateTime(Common.getCurrentTime());
		userDAO.add(user);
		return user;
	}

	/**
	 * 删除用户
	 */
	public void delUser(long userId) {
	    userDAO.delete(userId);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.greathack.user.serviceinterface.IUserService#mdfyUser(com.greathack.user
	 * .pojo.User)
	 */
	public void mdfyUser(User user) throws ServiceException {
		if(user.getSex()!=null){
			if(user.getSex()!=1 && user.getSex()!=2){
				logger.info("sex 不在取值范围内");
				throw new ServiceException(30001, "INVALID_PARAMS");
			}
		}
		User user1 = (User)userDAO.findById(user.getUserId());
		if(user1==null){
			throw new ServiceException(30005,"USER_DOES_NOT_EXIST");
		}
		user1.setMobile(user.getMobile());
		user1.setTel(user.getTel());
		user1.setEmail(user.getEmail());
		user1.setAvatar(user.getAvatar());
		user1.setBak1(user.getBak1());
		user1.setBak2(user.getBak2());
		user1.setBirthday(user.getBirthday());
		user1.setCertificationNo(user.getCertificationNo());
		user1.setChineseBirthday(user.getChineseBirthday());
		user1.setCompanyName(user.getCompanyName());
		user1.setNickName(user.getNickName());
		user1.setOutKey1(user.getOutKey1());
		user1.setOutKey2(user.getOutKey2());
		user1.setPosition(user.getPosition());
		user1.setSex(user.getSex());
		user1.setUserName(user.getUserName());
		user1.setIdcard(user.getIdcard());
		userDAO.update(user1);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.greathack.user.serviceinterface.IUserService#mdfyUserState(long,
	 * int)
	 */
	public void mdfyUserState(long userId, int userState) throws ServiceException {
	    if(userState<4){
	        logger.info("userState 不在取值范围内");
            throw new ServiceException(30001, "INVALID_PARAMS");
	    }
		List<Long> userStateList=Utils.longRecountToArrayList(userState);
		if(!userStateList.contains(userState)){
			logger.info("userState 不在取值范围内");
			throw new ServiceException(30001, "INVALID_PARAMS");
		}
		Object obj=userDAO.findById(userId);
		if(obj==null){
			throw new ServiceException(30005,"USER_DOES_NOT_EXIST");
		}
		User user = (User)obj;
		if(user.getUserState()== User.USER_STATE_DELETED){
			throw new ServiceException(30005,"USER_DOES_NOT_EXIST");
		}
		user.setUserState(userState);
		userDAO.update(user);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.greathack.user.serviceinterface.IUserService#getUser(long)
	 */
	public User getUser(long userId) {
		return userDAO.findById(userId);
	}

	@Override
	public List<User> getUserListByIds(List<Long> userIds) {
		return userDAO.getUserListByIds(userIds);
	}

	@Override
	public List<User> findByExample(User instance) {
		return userDAO.findByExample(instance);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.greathack.user.serviceinterface.IUserService#searchUsers(com.greathack
	 * .user.pojo.UserSearchCriteria)
	 */
	public List<User> searchUsers(UserSearchCriteria criteria){
		List<User> userList=new ArrayList<User>();
		if(criteria==null){
			return userList;
		}		
		return userDAO.search(criteria);
	}

	public static boolean isExist(long userId){
		Object user=userDAO.findById(userId);
		if(user==null){
			return false;
		}
		return true;
	}

	public long getSearchResultCount(UserSearchCriteria criteria) {
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
				count=Long.toString(userDAO.getSearchResultCount(criteria));
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
