/**
*@文件名：HolidayService.java
*@类路径：com.greathack.holiday.service
*/
package com.greathack.homlin.service.holiday;

import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.holiday.IHolidayDAO;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.holiday.Holiday;
import com.greathack.homlin.pojo.holiday.HolidaySearchCriteria;
import com.greathack.homlin.serviceinterface.holiday.IHolidayService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.utils.tools.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: 假日管理接口
 * @author greathack
 */
public class HolidayService implements IHolidayService {
	
	private static Logger logger = LoggerFactory.getLogger(HolidayService.class);
	private static IHolidayDAO holidayDAO = (IHolidayDAO) DAOFactory.createDAO("IHolidayDAO");

	@Override
	public Holiday add(Holiday holiday) throws ServiceException {
		if(Validation.isEmpty(holiday.getHolidayName())){//假日名称不能为空
		    logger.info("假日名称不能为空");
			throw new ServiceException(270002,"HOLIDAY_NAME_REQUIRED");
		}
		Holiday temp=holidayDAO.findByDate(holiday.getHolidayDate());
		if(temp!=null){
			logger.info("假日已经存在");
			throw new ServiceException(270004,"HOLIDAY_EXIST");
		}
		holiday.setHolidayId(IdCreator.createId("Holiday"));
		holidayDAO.add(holiday);
		return holiday;
	}

	@Override
	public void mdfy(Holiday holiday) throws ServiceException {
		Holiday temp=holidayDAO.findById(holiday.getHolidayId());
		if(temp==null){
			logger.info("假日不存在");
			throw new ServiceException(270003,"HOLIDAY_NOT_EXIST");
		}
		temp.setHolidayDate(holiday.getHolidayDate());
		temp.setHolidayName(holiday.getHolidayName());
		temp.setHolidayType(holiday.getHolidayType());
		temp.setIsRest(holiday.getIsRest());
		temp.setRemark(holiday.getRemark());
		holidayDAO.update(temp);
	}

	@Override
	public void delete(long holidayId) {
		holidayDAO.delete(holidayId);		
	}

	@Override
	public Holiday getHoliday(long holidayId) {
		return holidayDAO.findById(holidayId);
	}

    @Override
    public Holiday getHolidayByDate(Integer date) {
        return holidayDAO.findByDate(date);
    }

	/* (non-Javadoc)
	 * @see com.greathack.holiday.serviceinterface.IHolidayService#search(com.greathack.holiday.pojo.HolidaySearchCriteria)
	 */
	@Override
	public List<Holiday> search(HolidaySearchCriteria criteria) {
		List<Holiday> holidayList=new ArrayList<Holiday>();
		if(criteria==null){
			return holidayList;
		}
		return holidayDAO.search(criteria);
	}
	
	public long getSearchResultCount(HolidaySearchCriteria criteria) {
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
				count=Long.toString(holidayDAO.getSearchResultCount(criteria));
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
