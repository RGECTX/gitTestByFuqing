/** 
* @文件名：CircleService.java  
* @类路径：com.greathack.area.service  
* @描述：TODO
* @作者：greathack 
* @日期：2017年5月3日 
* @版本：V1.0  
*/
package com.greathack.homlin.service.area;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.area.ICircleDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.area.Circle;
import com.greathack.homlin.pojo.area.CircleSearchCriteria;
import com.greathack.homlin.serviceinterface.area.ICircleService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.TypeOption;
import com.greathack.utils.tools.Utils;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: TODO
 * @author greathack
 * @date 2017年5月3日
 * @version V1.0
 */
public class CircleService implements ICircleService {
	
	private static Logger logger = LoggerFactory.getLogger(CircleService.class);
	private static ICircleDAO circleDAO = (ICircleDAO) DAOFactory.createDAO("ICircleDAO");


	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.ICircleService#addCircle(com.greathack.area.pojo.Circle)
	 */
	@Override
	public Circle addCircle(Circle circle) throws ServiceException {
		if(Validation.isEmpty(circle.getAppCode())){
			throw new ServiceException(100001,"INVALID_PARAMS");
		}
		if(TypeOption.nullToIntZero(circle.getAreaId())==0){
			throw new ServiceException(100001,"INVALID_PARAMS");
		}
		if(Validation.isEmpty(circle.getCircleName())){
			throw new ServiceException(100001,"INVALID_PARAMS");
		}
		circle.setCircleState(TypeOption.nullToValue(circle.getCircleState(), 1));
		List<Integer> states=Utils.recountToArrayList(3);
		if(!states.contains(circle.getCircleState())){
			throw new ServiceException(100001,"INVALID_PARAMS");
		}
		
		circle.setCreateTime(Common.getCurrentTime());
		circle.setCircleId(IdCreator.createId("Circle"));
		circleDAO.add(circle);
		return circle;
	}

	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.ICircleService#delCircle(long)
	 */
	@Override
	public void delCircle(long circleId) {
		Circle circle=circleDAO.findById(circleId);
		if(circle==null){
			return;
		}
		circle.setCircleState(Circle.CIRCLE_STATE_DELETED);
		circleDAO.update(circle);

	}

	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.ICircleService#disableCircle(long)
	 */
	@Override
	public void disableCircle(long circleId) throws ServiceException {
		Circle circle=circleDAO.findById(circleId);
		if(circle==null){
			throw new ServiceException(100002,"CIRCLE_NOT_EXIST");
		}
		circle.setCircleState(Circle.CIRCLE_STATE_LOCKED);
		circleDAO.update(circle);

	}

	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.ICircleService#enableCircle(long)
	 */
	@Override
	public void enableCircle(long circleId) throws ServiceException {
		Circle circle=circleDAO.findById(circleId);
		if(circle==null){
			throw new ServiceException(100002,"CIRCLE_NOT_EXIST");
		}
		circle.setCircleState(Circle.CIRCLE_STATE_NORMAL);
		circleDAO.update(circle);

	}

	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.ICircleService#mdfyCircle(com.greathack.area.pojo.Circle)
	 */
	@Override
	public void mdfyCircle(Circle circle) throws ServiceException {
		Circle temp=circleDAO.findById(circle.getCircleId());
		if(temp==null){
			throw new ServiceException(100002,"CIRCLE_NOT_EXIST");
		}
		circle.setAppCode(temp.getAppCode());
		circle.setCircleState(temp.getCircleState());
		circle.setCreateTime(temp.getCreateTime());
		circleDAO.update(circle);
	}

	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.ICircleService#getCircle(long)
	 */
	@Override
	public Circle getCircle(long circleId) {
		return circleDAO.findById(circleId);
	}

	/* (non-Javadoc)
	 * @see com.greathack.area.serviceinterface.ICircleService#search(com.greathack.area.pojo.CircleSearchCriteria)
	 */
	@Override
	public List<Circle> search(CircleSearchCriteria criteria) {
		List<Circle> circleList=new ArrayList<Circle>();
		if(criteria==null){
			return circleList;
		}
		return circleDAO.search(criteria);
	}
	
	public long getSearchResultCount(CircleSearchCriteria criteria) {
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
				count=Long.toString(circleDAO.getSearchResultCount(criteria));
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
